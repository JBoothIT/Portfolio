using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace Engraver_s_Friend
{
    /*
     * Application Author: Jeremy Booth
     * The function of this application is to store and retrieve information pertaining to jobs typically 
     * performed by a user in an engraving profession. Sort of like a filing cabinet. 
     * Upon completion this application will allow the user insert and retrieve information from a locally stored database
     * and display it to a user-friendly interface. This application will allow the user to create and import previously
     * created databases. In addition, this application will allow the import of MS Excel spreadsheets to populate a database. 
     */
    public partial class Main : Form
    {
        public Main()
        {
            InitializeComponent();
        }

        //Functions to be performed at application load
        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                if (!File.Exists("PATH.txt") || File.ReadLines("PATH.txt").ToString() != null)
                {
                    //CreateFile f2 = new CreateFile();
                    //f2.ShowDialog();
                }
                else
                {
                    
                }
            }
            catch(FileNotFoundException)
            {
                CreateFile f2 = new CreateFile();
                f2.ShowDialog();
            }
            

            //Displays the application build version
            VersionBox.Text = "Version: " + Application.ProductVersion.ToString();

            //Setup for email placeholder
            EmailInsBox.Text = "sample@someEmail.com";
            EmailInsBox.ForeColor = Color.Gray;
        }

        //Menu Strip: Import Button - Opens ImportForm
        private void ExitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ImportForm f1 = new ImportForm();
            f1.ShowDialog();
        }

        //Menu Strip: Exit Button
        private void ExitToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        //Insert/Submit Button Functions
        private void InsButton_Click(object sender, EventArgs e)
        {
            if(InsButton.Text == "INSERT")
            {
                CompanyBox.Visible = false;
                TypeBox.Visible = false;
                SizeBox.Visible = false;
                MaterialBox.Visible = false;
                BevBox.Visible = false;
                EmailBox.Visible = false;
                PhoneBox.Visible = false;
                CompanyInsBox.Visible = true;
                MatInsBox.Visible = true;
                MatNumLabel.Visible = true;
                SizeInsBox.Visible = true;
                TypeInsBox.Visible = true;
                BevInsBox.Visible = true;
                PhoneInsBox.Visible = true;

                EmailInsBox.Text = "sample@someEmail.com";
                EmailInsBox.ForeColor = Color.Gray;

                InsButton.Text = "SUBMIT";
            }
            else if(InsButton.Text == "SUBMIT")
            {
                CompanyBox.Visible = true;
                TypeBox.Visible = true;
                SizeBox.Visible = true;
                MaterialBox.Visible = true;
                BevBox.Visible = true;
                EmailBox.Visible = true;
                PhoneBox.Visible = true;
                CompanyInsBox.Visible = false;
                MatInsBox.Visible = false;
                MatNumLabel.Visible = false;
                SizeInsBox.Visible = false;
                TypeInsBox.Visible = false;
                BevInsBox.Visible = false;
                PhoneInsBox.Visible = false;
                InsButton.Text = "INSERT";
            }
            else
            {
                MessageBox.Show("A critical error has occurred!", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                Application.Exit();
            }
        }

        //Email Box: Onclick placeholder is erased
        private void EmailInsBox_Enter(object sender, EventArgs e)
        {
            if (EmailInsBox.Text == "sample@someEmail.com")
            {
                EmailInsBox.Text = "";
                EmailInsBox.ForeColor = Color.Black;
            }
        }

        //Email Box: If left blank placeholder is typed in textbox
        private void EmailInsBox_Leave(object sender, EventArgs e)
        {
            if (EmailInsBox.Text == "")
            {
                EmailInsBox.Text = "sample@someEmail.com";
                EmailInsBox.ForeColor = Color.Gray;
            }
        }

        private void createToolStripMenuItem_Click(object sender, EventArgs e)
        {
            CreateFile f3 = new CreateFile();
            f3.ShowDialog();
        }

        private void aboutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            AboutBox f1 = new AboutBox();
            f1.ShowDialog();
        }
    }
}
