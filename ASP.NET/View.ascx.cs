/*
' Copyright (c) 2013  Christoc.com
'  All rights reserved.
' 
' THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
' TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
' THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
' CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
' DEALINGS IN THE SOFTWARE.
' 
*/

using System;
using DotNetNuke.Security;
using DotNetNuke.Services.Exceptions;
using DotNetNuke.Entities.Modules;
using DotNetNuke.Entities.Modules.Actions;
using DotNetNuke.Services.Localization;
using System.Net.Mail;
using System.Collections.Generic;
using System.Web.UI.WebControls;

namespace Christoc.Modules.RTF
{
    /// -----------------------------------------------------------------------------
    /// <summary>
    /// The View class displays the content
    /// 
    /// Typically your view control would be used to display content or functionality in your module.
    /// 
    /// View may be the only control you have in your project depending on the complexity of your module
    /// 
    /// Because the control inherits from RTFModuleBase you have access to any custom properties
    /// defined there, as well as properties from DNN such as PortalId, ModuleId, TabId, UserId and many more.
    /// 
    /// </summary>
    /// -----------------------------------------------------------------------------
    public partial class View : RTFModuleBase, IActionable
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                //if (Page.IsPostBack && Calendar1.SelectedDates.Count == 1)
                //    Calendar1.SelectedDates.Clear(); 
                //if (!IsPostBack)
                //{
                //    Calendar1.SelectedDates.Clear();
                //    calList.Clear();
                //}
            }
            catch (Exception exc) //Module failed to load
            {
                Exceptions.ProcessModuleLoadException(this, exc);
            }
        }

        //=====================================================================================================
        // http://geekswithblogs.net/dotNETvinz/archive/2009/03/09/tiptrick-aspnet-calendar-control-with-multiple-date-selections.aspx

        public static List<DateTime> calList = new List<DateTime>();

        protected void Calendar1_DayRender(object sender, DayRenderEventArgs e)
        {
            if (e.Day.IsSelected == true)
            {
                calList.Add(e.Day.Date);
            }
            Session["SelectedDates"] = calList;
        }


        protected void Calendar1_SelectionChanged(object sender, EventArgs e)
        {
            if (Session["SelectedDates"] != null)
            {
                List<DateTime> newList = (List<DateTime>)Session["SelectedDates"];
                foreach (DateTime dt in newList)
                {
                    if (Calendar1.SelectedDates.Contains(dt) || Calendar1.SelectedDate == dt)
                    {
                        Calendar1.SelectedDates.Remove(dt);
                    }
                    else
                        Calendar1.SelectedDates.Add(dt);
                    
                }
                calList.Clear();
            }
        }

        //=====================================================================================================

        //protected void Button3_Click(object sender, EventArgs e)
        //{
        //    TalentInfo.Visible = false;
        //    ContactInfo.Visible = true;
        //}

        //protected void Button4_Click(object sender, EventArgs e)
        //{
        //    TalentInfo.Visible = true;
        //    ContactInfo.Visible = false;
        //}

        protected void Button2_Click(object sender, EventArgs e)
        {
            string mySelectedDates = "";
            if (Session["SelectedDates"] != null)
            {
                List<DateTime> newList = (List<DateTime>)Session["SelectedDates"];
                foreach (DateTime dt in newList)
                {
                    //Response.Write(dt.ToShortDateString() + "<BR/>");
                    if(mySelectedDates == "")
                        mySelectedDates = dt.ToShortDateString();
                    else
                        mySelectedDates = mySelectedDates + ", " + dt.ToShortDateString();
                }
            }
            Calendar1.SelectedDates.Clear();
            calList.Clear();
            Session["SelectedDates"] = null;



            string fileName = Server.MapPath("~/App_Data/MailFile.txt");
            string mailBody = System.IO.File.ReadAllText(fileName);

            SmtpClient mySmtpClient = new SmtpClient();
            mySmtpClient.DeliveryMethod = SmtpDeliveryMethod.Network;
            mySmtpClient.EnableSsl = true;
            mySmtpClient.Host = "smtp.gmail.com";
            mySmtpClient.Port = 587;

            System.Net.NetworkCredential credentials = new System.Net.NetworkCredential("chris@chriskee.com", "H4nn4h1");
            mySmtpClient.UseDefaultCredentials = false;
            mySmtpClient.Credentials = credentials;

            MailMessage myMessage = new MailMessage();
            myMessage.Subject = "New Talent Request Form";
            mailBody = mailBody.Replace("##Name##", TextBox6.Text);
            mailBody = mailBody.Replace("##TitlePosition##", TitlePosition.Text);
            mailBody = mailBody.Replace("##Organization##", TextBox8.Text);
            mailBody = mailBody.Replace("##OfficePhone##", TextBox9.Text);
            mailBody = mailBody.Replace("##CellPhone##", TextBox10.Text);
            mailBody = mailBody.Replace("##Email##", TextBox11.Text);
            mailBody = mailBody.Replace("##HearAbout##", TextBox12.Text);
            mailBody = mailBody.Replace("##ProjType##", ProjectType.SelectedValue);
            mailBody = mailBody.Replace("##ProjDescription##", ProjectDescription.Text);
            mailBody = mailBody.Replace("##Usage##", Usage.Text);
            mailBody = mailBody.Replace("##TalentNeeds##", TextBox1.Text);
            mailBody = mailBody.Replace("##TalentNames##", TextBox2.Text);
            mailBody = mailBody.Replace("##LowBud##", TextBox3.Text);
            mailBody = mailBody.Replace("##HighBud##",TextBox4.Text);
            if (Yes.Checked)
                mailBody = mailBody.Replace("##OutState##", "Yes");
            else if(No.Checked)
                mailBody = mailBody.Replace("##OutState##", "No");
            else
                mailBody = mailBody.Replace("##OutState##", "N/A");
            //mailBody = mailBody.Replace("##EventDate##", Calendar1.SelectedDate.ToString());
            mailBody = mailBody.Replace("##EventDate##", mySelectedDates);
            mailBody = mailBody.Replace("##EventLoc##", EventLoc.Text);
            mailBody = mailBody.Replace("##OtherInfo##", OtherInfo.Text);
            myMessage.Body = mailBody;

            myMessage.From = new MailAddress("jkolinofsky@callidusagency.com", "Callidus Talent Request");
            myMessage.To.Add(new MailAddress("jkolinofsky@callidusagency.com", "John Kolinofsky"));

            try
            {
                mySmtpClient.Send(myMessage);
                ContactInfo.Visible = false;
                TalentInfo.Visible = false;
                mailStatus.Text = "Talent Request was sent!";
                mailStatus.Visible = true;
                
            }
            catch (Exception ex)
            {
                ContactInfo.Visible = false;
                TalentInfo.Visible = false;
                mailStatus.CssClass = "lblError";
                mailStatus.Text = "Error. Talent Request not sent.";
                mailStatus.Visible = true;
            }
        }

        public ModuleActionCollection ModuleActions
        {
            get
            {
                var actions = new ModuleActionCollection
                    {
                        {
                            GetNextActionID(), Localization.GetString("EditModule", LocalResourceFile), "", "", "",
                            EditUrl(), false, SecurityAccessLevel.Edit, true, false
                        }
                    };
                return actions;
            }
        }
    }
}