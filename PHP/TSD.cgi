#!/usr/local/bin/php
<!DOCTYPE html>
<html>
<head>
<title>TSD</title>
<meta charset="utf-8" />
<style type="text/css">
th {background-color:#FFD685;}
td {text-align:center;}
h3 {color:red;}
h1 {font-family:"Verdana";}
</style>
</head>
<body style="background-color:#CCFFCC;">
<h1 style="text-align:center;">Television Show Dataminer</h1>
<div align="center">
<?php
// This script was developed by Chris Kee

$data_file_name = 'datafile.dat';  // global decloration of file name
require_once 'arrayToXML.mod';	// used for arrayToXML call

// Methods for TSD script
//===================================================================

function build_show_list_form(&$Show_List) // Display Show List Interface
{
	if(count($Show_List) == 0) // build blank form
	{
// Show List form header		
echo <<<SLFH
<form method="post" action"">
<table border="1">
	<tr>
		<th>Del</th>
		<th>Show</th>
		<th>Season</th>
		<th>Episodes</th>
	</tr>		
SLFH;
for($i = 0; $i < 5; $i++)
{
	echo "\n\t<tr>\n";
	echo "\t\t<td>&nbsp;</td>\n";
	echo "\t\t<td><input type=\"text\" name=\"Shows[{$i}][Show_Name]\" size=\"15\"></td>\n";
	echo "\t\t<td><input type=\"text\" name=\"Shows[{$i}][Season]\" size=\"4\"></td>\n";
	echo "\t\t<td>\n";
	echo "\t\t<table>\n";
	echo "\t\t<tr>\n";
	for($j = 1; $j <= 30; $j++)
	{
		if($j == 1 || $j % 5 == 0)  // display numbers in checkbox field
		{
			echo "\t\t\t<td>".$j."<br/><input type=\"checkbox\" name=\"Shows[{$i}][Episodes][{$j}]\"></td>\n";
		}
		else
			echo "\t\t\t<td><br/><input type=\"checkbox\" name=\"Shows[{$i}][Episodes][{$j}]\"></td>\n";
	}
	echo "\t\t</tr>\n";
	echo "\t\t</table>\n";
	echo "\t\t</td>\n";
	echo "\t</tr>\n";
}

echo "</table>\n";
echo "<input type=\"submit\" name=\"update_list\" value=\"Update Show List\">\n";
echo "&nbsp;<input type=\"submit\" name=\"search_shows\" value=\"Search\">\n";
echo "</from>\n";

	}
	else // build form with data from file
	{

// Show List form header	
echo <<<SLFH
<form method="post" action"">
<table border="1">
	<tr>
		<th>Del</th>
		<th>Show</th>
		<th>Season</th>
		<th>Episodes</th>
	</tr>		
SLFH;

$i = 0;
foreach($Show_List as $data)
{
	echo "\n\t<tr>\n";
	echo "\t\t<td><input type=\"checkbox\" name=\"Deleted[{$i}]\" value=\"0\"></td>\n";
	echo "\t\t<td><input type=\"text\" name=\"Shows[{$i}][Show_Name]\" size=\"15\" value=\"{$data[Show_Name]}\"></td>\n";
	echo "\t\t<td><input type=\"text\" name=\"Shows[{$i}][Season]\" size=\"4\" value=\"{$data[Season]}\"></td>\n";
	echo "\t\t<td>\n";
	echo "\t\t<table>\n";
	echo "\t\t<tr>\n";
	for($j = 1; $j <= 30; $j++)
	{
		if($j == 1 || $j % 5 == 0)  // display numbers in checkbox field
		{
			if($data[Episodes][$j] == 'on')
				echo "\t\t\t<td>".$j."<br/><input type=\"checkbox\" name=\"Shows[{$i}][Episodes][{$j}]\" checked></td>\n";
			else
				echo "\t\t\t<td>".$j."<br/><input type=\"checkbox\" name=\"Shows[{$i}][Episodes][{$j}]\"></td>\n";
		}
		else
		{
			if($data[Episodes][$j] == 'on')
				echo "\t\t\t<td><br/><input type=\"checkbox\" name=\"Shows[{$i}][Episodes][{$j}]\" checked></td>\n";
			else
				echo "\t\t\t<td><br/><input type=\"checkbox\" name=\"Shows[{$i}][Episodes][{$j}]\"></td>\n";
		}
			
	}
	echo "\t\t</tr>\n";
	echo "\t\t</table>\n";
	echo "\t\t</td>\n";
	echo "\t</tr>\n";
$i++;
} //end foreach


//add additional rows for user input
$Extra_Input = 3;
if(count($Show_List) < 3)
	$Extra_Input = 5 - count($Show_List) + 1;
	
for($i = count($Show_List) + 1; $i < count($Show_List) + $Extra_Input; $i++)
{
	echo "\n\t<tr>\n";
	echo "\t\t<td>&nbsp;</td>\n";
	echo "\t\t<td><input type=\"text\" name=\"Shows[{$i}][Show_Name]\" size=\"15\"></td>\n";
	echo "\t\t<td><input type=\"text\" name=\"Shows[{$i}][Season]\" size=\"4\"></td>\n";
	echo "\t\t<td>\n";
	echo "\t\t<table>\n";
	echo "\t\t<tr>\n";
	for($j = 1; $j <= 30; $j++)
	{
		if($j == 1 || $j % 5 == 0)  // display numbers in checkbox field
		{
			echo "\t\t\t<td>".$j."<br/><input type=\"checkbox\" name=\"Shows[{$i}][Episodes][{$j}]\"></td>\n";
		}
		else
			echo "\t\t\t<td><br/><input type=\"checkbox\" name=\"Shows[{$i}][Episodes][{$j}]\"></td>\n";
	}
	echo "\t\t</tr>\n";
	echo "\t\t</table>\n";
	echo "\t\t</td>\n";
	echo "\t</tr>\n";
}


echo "</table>\n";
echo "<input type=\"submit\" name=\"update_list\" value=\"Update Show List\">\n";
echo "&nbsp;<input type=\"submit\" name=\"search_shows\" value=\"Search\">\n";
echo "</from>\n";

	}
}

function torrent_search($Show_List)  // Search and Results
{
	$result_list = array();
	foreach($Show_List as $show)
	{
		$program = $show[Show_Name];
		$season = sprintf('%02d',$show[Season]);
		$search = 'http://www.cse.unt.edu/~donr/courses/4410/13summer/project/getXMLfake.php?q=' .
                               urlencode($program) . "+s$season";
        $xml = file_get_contents($search);	// search and retrieve xml
        $matches = ArrayToXML::toArray($xml); // convert to associative array

        if(isset($matches['channel']['item']))  // check for propper return of results
        {
        	foreach ($matches['channel']['item'] as $values)
			{
				for($i = 1; $i <=30; $i++)
				{
					if(!isset($show['Episodes'][$i]))  // get episodes that are not checked
					{
						$num = sprintf('%02d',$i);
						if(strstr($values['title'], "S{$season}E{$num}"))
						{
							$title = $values['title'];
							list (,$size,$byte,,$seed,,$peer) = explode(' ',$values['description']);
							$size = $size . " " . $byte;
							$t_link = $values['link'];
							array_push($result_list,array('title' => $title, 'link' => $t_link, 'size' => $size, 'seed' => $seed, 'peer' => $peer));
						}
						
					}
				}
				
			}
        }
        else  // bad results
        	echo "<span style=\"color:red;font-size:large;\">Error: ".$show[Show_Name]." season " .$show[Season]. " could not be found</span><br />\n";
	}
	
	if(count($result_list) == 0) // empty result list so redisplay Show List
	{
		// alert user no results found and return to Show List Interface
		echo "<script type=\"text/javascript\">\n";
		echo "alert('No search results.')\n";
		echo "</script>\n";
		build_show_list_form($Show_List);
	}
	else	// display Search Results
	{
		// create file for list of results
		$append_data = '$result_list = ' . var_export($result_list,true) . ";\n";
		$fh = fopen('results.dat', 'w');
		fputs ($fh,$append_data);
		fclose($fh);
	
		echo "<form method=\"post\" action\"\">\n";
		echo "<table border=\"1\">\n";
		echo "<tr>\n";
		echo "<th>&nbsp;</th>\n";
		echo "<th>Show Name</th>\n";
		echo "<th>Size</th>\n";
		echo "<th>Seeds</th>\n";
		echo "<th>Peers</th>\n";
		echo "</tr>\n";
	
		$i = 0;
		foreach($result_list as $data)
		{

			echo "\t<tr>\n";
			echo "\t\t<td><input type=\"checkbox\" name=\"Checked[{$i}]\"</td>\n";
			echo "\t\t<td><a href=\"{$data['link']}\" target=\"_blank\">{$data['title']}</a></td>\n";
			echo "\t\t<td>{$data['size']}</td>\n";
			echo "\t\t<td>{$data['seed']}</td>\n";
			echo "\t\t<td>{$data['peer']}</td>\n";
			echo "\t</tr>\n";
		
			$i++;
		}
	
		echo "</table>\n";
		echo "<input type=\"submit\" name=\"update_list2\" value=\"Update Show List\">\n";
		echo "<input type=\"submit\" name=\"return\" value=\"Return\">\n";
		echo "</from>\n";
	}
	
	

}


//===================================================================
// end of Methods

	if(count($_POST) == 0 || isset($_POST['update_list']) || isset($_POST['return']))  // first page load or update
	{
		if(isset($_POST['update_list'])) // update file
		{
			
			// compare arrays
			if(file_exists($data_file_name))
				eval(file_get_contents($data_file_name));
			if(is_null($Show_List) || !isset($Show_List))
			{
				$Show_List = array();
			}

			$Shows = array();
			$test_shows = array();
			
			
			foreach($_POST['Shows'] as $data)
			{
				if($data[Show_Name] !== '' && (is_numeric($data[Season]) && $data[Season] > 0 && $data[Season] < 100))
				{
					trim($data[Show_Name]);
					array_push($Shows, $data);
					
				}
				
				
			}
			
			// Check for updates to a row
			for($i = 0; $i < count($Show_List); $i++)
			{
				if($Show_List[$i][Show_Name] != $Shows[$i][Show_Name] && $Shows[$i][Show_Name] != NULL)
					$Show_List[$i][Show_Name] = $Shows[$i][Show_Name];
				if($Show_List[$i][Season] != $Shows[$i][Season] && $Shows[$i][Season] != NULL)
					$Show_List[$i][Season] = $Shows[$i][Season];
				$Show_List[$i][Episodes] = $Shows[$i][Episodes];
			}
			
			$test_shows2 = array();
			foreach($Show_List as $data)
			{
				$test_string = $data[Show_Name] . " season {$data[Season]}";
				array_push($test_shows2, $test_string);
			}
			
			
			foreach($Shows as $data)
			{
				$test_string = $data[Show_Name] . " season {$data[Season]}";
				array_push($test_shows, $test_string);
				$new_shows = array_diff($test_shows, $test_shows2);
				foreach($new_shows as $added)
				{
					echo "<span style=\"color:green;\">Added \"" .$added."\"</span><br />\n";
					array_push($Show_List, $data);
				}
				array_pop($test_shows);
			}
			
			// handle deletes
			if(isset($_POST['Deleted']))
			{
				//print_r($_POST['Deleted']);
				for($i = 0; $i < count($Shows); $i++)
				{
					if(isset($_POST['Deleted'][$i]))
					{
						unset($Show_List[$i]);
						
						echo "<span style=\"color:red;\">Deleted \"" .$Shows[$i][Show_Name]. " season " .$Shows[$i][Season]. "\"</span><br />\n";
					}
				}
				$Show_List = array_merge($Show_List);
			}

			$data = '$Show_List = ' . var_export($Show_List,true) . ";\n";
			$fh = fopen($data_file_name, 'w');
			fputs($fh, $data);
			fclose($fh);

		}
		
		
		// show_list_form
		if(file_exists($data_file_name))
		{
			eval(file_get_contents($data_file_name));
			
			if(isset($Show_List) && is_array($Show_List))
			{
				// build form with data form file
				build_show_list_form($Show_List);
				
			}
			else
			{
				// build blank form
				$Show_List = null;
				build_show_list_form($Show_List);
			}
		}

		else  // build blank form and create blank data file if data file does not exist
		{
			$data = '$Show_List = ' . var_export($Show_List,true) . ";\n";
			$fh = fopen($data_file_name, 'w');
			fputs($fh, $data);
			fclose($fh);
			$Show_List = null;
			build_show_list_form($Show_List);
		}
		
	}
	
	if(isset($_POST['update_list2'])) // update Show List after checking Search Results
	{
	
		eval(file_get_contents($data_file_name));
		eval(file_get_contents('results.dat'));
		
		$displayUpdates = array(); // array used to display updates after checks are evaluated

		for($i = 0; $i < count($result_list); $i++)
		{
			
			if(isset($_POST['Checked'][$i]))
			{
				// extract data to compare and search to Show List data
				$test = $result_list[$i][title];
				list($show, $SE) = explode(' ', $test);
				// find season number and episode number of desired check
				list(,$test) = explode('S', $SE);
				list($season,$episode) = explode('E', $test);
				$season = intval($season);
				$episode = intval($episode);
				for($j=0;$j<count($Show_List);$j++)
				{
					
					if(strtoupper($Show_List[$j][Show_Name]) == strtoupper($show) && $Show_List[$j][Season] == $season)
					{
						$Show_List[$j][Episodes][$episode] = 'on';
						
						// add item to displayUpdates
						$testItem = $Show_List[$j][Show_Name] . " season " . $Show_List[$j][Season];
						if(!in_array($testItem, $displayUpdates)) // don't allow duplicate Show, Seasons to display
							array_push($displayUpdates, $testItem);
		
					}	
					
				}
				
			}
		}
		
		// display updates to user
		foreach($displayUpdates as $data1)
		{
			echo "<span style=\"color:green;\">Updated \"" . $data1 . "\"</span>\n<br />";
		}
		
		
		$data = '$Show_List = ' . var_export($Show_List,true) . ";\n";
		$fh = fopen($data_file_name, 'w');
		fputs($fh, $data);
		fclose($fh);
		build_show_list_form($Show_List);
		
	}
	
	if(isset($_POST['search_shows'])) // Search
	{
		eval(file_get_contents($data_file_name));
			
		if(isset($Show_List) && is_array($Show_List))
		{
			if(count($Show_List) == 0)
			{
				echo "<script type=\"text/javascript\">\n";
				echo "alert('No search could be performed. No shows were added to \"Show List\".')\n";
				echo "</script>\n";
				$Show_List = null;
				build_show_list_form($Show_List);
			}
			else // SEARCH!!
			{
				torrent_search($Show_List);
			}
		}
		else // error occured with file
		{
			echo "<script type=\"text/javascript\">\n";
			echo "alert('Error. Bad file. Could not perform search.')\n";
			echo "</script>\n";
			$Show_List = null;
			build_show_list_form($Show_List);
		}
	}
?>
</div>
<div align="center"></div>
</body>
</html>