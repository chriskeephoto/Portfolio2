TSD.cgi Read Me

Project Type: Class Project
Developer: Chris Kee
url: http://students.cse.unt.edu/~crk0087/CSCE_4410/TSD.cgi

TSD or Television Show Dataminer is a web application that
allows users to search for television shows through a bit-torrent
search engine. The application then allows the user to organize
which shows have been downloaded.

For the purposes of this application, it is functional to pull
XML data that contains television show data from a php site created 
by Professor Don Ratzlef.  This php site contains specific television
shows that were pulled from torrentz.net.  Don's php site is required
as a middle ground site becuase torrentz.net block server requests from 
the schools web server.

http://www.cse.unt.edu/~donr/courses/4410/13summer/project/getXMLfake.php?query

where query is one of the following predefined show searches:

q=crossing+lines+s01
q=csi+s10
q=fringe+s01
q=fringe+s05
q=graceland+s01
q=how+i+met+your+mother+s05
q=king+and+maxwell+s01
q=major+crimes+s02
q=motive+s01
q=true+blood+s04

These are the only quieries that the script recognizes — the instructor made each of these 
specific queries and saved the output in local files that are accessed when you run the script. 
No other parameters will be recognized.