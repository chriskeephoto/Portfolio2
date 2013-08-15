<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="View.ascx.cs" Inherits="Christoc.Modules.RTF.View" %>
<style type="text/css">
    .lblError
    {
        color:#B84D4D;
    }
</style>
<span style="font-size: 24px;">Request Talent</span><hr>
<br>
<%--<table>
<th style="font-size:large;"><u>Talent Info</u></th>
<th style="font-size:large;"><u>Contact Info</u></th>
<tr>
<td>--%>
<table>
    <tr>
        <td >
<asp:Table ID="TalentInfo" runat="server">
    <asp:TableRow HorizontalAlign="Center">
        <asp:TableCell ColumnSpan="2" HorizontalAlign="Center">
            <span style="font-size:large; text-align:center;"><b><u>Talent Information</u></b></span>
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow HorizontalAlign="Center">
        <asp:TableCell ColumnSpan="2" HorizontalAlign="Center">
            <span style="font-size:large; text-align:center;">&nbsp;</span>
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Project Type:
        </asp:TableCell>
        <asp:TableCell>
            <asp:DropDownList ID="ProjectType" runat="server">
                <asp:ListItem Value="Comercial">Comercial</asp:ListItem>
                <asp:ListItem Value="Print">Print</asp:ListItem>
                <asp:ListItem Value="FilmVideo">Film & Video</asp:ListItem>
                <asp:ListItem Value="Industrial">Industrial</asp:ListItem>
                <asp:ListItem Value="Runway">Runway</asp:ListItem>
                <asp:ListItem Value="ShowLive">Show / Live Presentation</asp:ListItem>
                <asp:ListItem Value="CelebrityConsulting">Celebrity Consulting</asp:ListItem>
                <asp:ListItem Value="Other">Other</asp:ListItem>
            </asp:DropDownList>
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Project Description:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="ProjectDescription" runat="server" TextMode="multiline" Height="40" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Describe Project Usage:<br /><span style="font-size:small;">(period, territory, media)</span>
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="Usage" runat="server" TextMode="multiline" Height="40" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Describe specific talent needs:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox1" runat="server" TextMode="multiline" Height="40" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Names of any specific<br />talent in mind?
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox2" runat="server" TextMode="multiline" Height="40" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Budget range per talent?
        </asp:TableCell>
        <asp:TableCell>
            Low: &nbsp;<asp:TextBox ID="TextBox3" runat="server" Width="134" /> <br />High: <asp:TextBox ID="TextBox4" runat="server" Width="134" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Open to considering<br />out of state talent?
        </asp:TableCell>
        <asp:TableCell>
            Yes:&nbsp;<asp:RadioButton ID="Yes" runat="server" GroupName="OutOfState"/>
            &nbsp;&nbsp;No:&nbsp;<asp:RadioButton ID="No" runat="server" GroupName="OutOfState"/>
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Planned day of shoot<br />or event?
        </asp:TableCell>
        <asp:TableCell>
            <asp:Calendar ID="Calendar1" runat="server" OnDayRender="Calendar1_DayRender" OnSelectionChanged="Calendar1_SelectionChanged"></asp:Calendar>
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Location of shoot<br />or event?
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="EventLoc" runat="server" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Any other info?
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="OtherInfo" runat="server" TextMode="multiline" Height="40" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow HorizontalAlign="Right">
        <asp:TableCell> 
            <%--<asp:Button ID="Reset_Button" runat="server" Text="Cancel" Width="81px" OnClientClick="this.form.reset();return false;" style="width:80px;height:30px;"/>--%>
        </asp:TableCell>
        <asp:TableCell HorizontalAlign="Right">
           <%-- <asp:Button ID="Button3" runat="server" Text="Next >>" OnClick="Button3_Click" style="background-color:#4DB8FF;width:80px;height:30px;"/>--%>
        </asp:TableCell>
    </asp:TableRow>

</asp:Table>
</td>
<td style="vertical-align:top;padding:0px 0px 0px 80px;">
<asp:Table ID="ContactInfo" runat="server">
        <asp:TableRow HorizontalAlign="Center">
        <asp:TableCell ColumnSpan="2" HorizontalAlign="Center">
            <span style="font-size:large; text-align:center;"><b><u>Contact Information</u></b></span>
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow HorizontalAlign="Center">
        <asp:TableCell ColumnSpan="2" HorizontalAlign="Center">
            <span style="font-size:large; text-align:center;">&nbsp;</span>
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Your Name:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox6" runat="server" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Title / Position:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TitlePosition" runat="server" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Organization:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox8" runat="server" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Office Phone:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox9" runat="server" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Cell Phone:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox10" runat="server" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            Email:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox11" runat="server" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
            How did you hear<br />about us?
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox12" runat="server" TextMode="multiline" Height="40" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
    <asp:TableRow>
        <asp:TableCell>
          <%--  <asp:Button ID="Button4" runat="server" Text="<< Back" OnClick="Button4_Click" style="background-color:#4DB8FF;width:80px;height:30px;"/>--%>
        </asp:TableCell>
        <asp:TableCell>
            <asp:Button ID="Button2" runat="server" Text="Submit" OnClick="Button2_Click" style="background-color:#4DB8FF;width:80px;height:30px;"/>
        </asp:TableCell>
    </asp:TableRow>
</asp:Table>

<asp:Label ID="mailStatus" runat="server" Visible="false" style="font-size:large"/>
            </td>
</tr>
</table>
<%--</td>
 </tr>
</table>--%>

<%--<asp:Table ID="Page Table" runat="server">
<asp:TableRow>
<asp:TableCell>--%>


<%--</asp:TableCell>--%>
<%--<asp:TableCell>
<asp:Table ID="Table2" runat="server">
    <asp:TableRow>
        <asp:TableCell>
            Your Name:
        </asp:TableCell>
        <asp:TableCell>
            <asp:TextBox ID="TextBox6" runat="server" Width="170" />
        </asp:TableCell>
    </asp:TableRow>
</asp:Table>
</asp:TableCell>--%>
<%-- </asp:TableRow>
</asp:Table>--%>

<%--Message: <asp:TextBox ID="CallidusMessage" runat="server" Width="150px" /> <br />
<asp:Button ID="Button1" runat="server" Text="Send" OnClick="Button1_Click" />

<asp:Label ID="lblmyLBL" runat="server" />
<asp:Label ID="lblmyLBL2" runat="server" />--%>


