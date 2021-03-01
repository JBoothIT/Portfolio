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
using System.Data.SqlServerCe;


namespace Engraver_s_Friend
{
    public partial class CreateFile : Form
    {
        public CreateFile()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ImportForm f1 = new ImportForm();
            f1.ShowDialog();
        }


        private void button4_Click(object sender, EventArgs e)
        {
            try
            {
                FolderBrowserDialog foldrA = new FolderBrowserDialog();
                if (foldrA.ShowDialog() == DialogResult.OK)
                {
                    PATHBox.Text = foldrA.SelectedPath;
                }
            }
            catch
            {

            }
        }

        private void CreateB_Click(object sender, EventArgs e)
        {
            
            if (PATHBox.Text == "" || FileBox.Text == "")
            {
                MessageBox.Show("Please ensure all fields are filled.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                String connectionString;
                String fileName = PATHBox.Text + "\\" + FileBox.Text + ".sdf";
                string password = "password";
                if (File.Exists(fileName))
                {
                    MessageBox.Show("A database with that name already exists!" + "\n" + " Are you sure you want to override it?", "Error!", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                    if (DialogResult == DialogResult.Yes)
                    {
                        File.Delete(fileName);
                        connectionString = String.Format("DataSource=\"{0}\"; Password='{1}'", fileName, password);
                        SqlCeEngine en = new SqlCeEngine(connectionString);
                        en.CreateDatabase();
                        MessageBox.Show("The database has been created!", "Success!", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        if (DialogResult == DialogResult.OK)
                        {
                            Close();
                        }
                    }
                }
                else
                {

                    File.Delete(fileName);
                    connectionString = String.Format("DataSource=\"{0}\"; Password='{1}'", fileName, password);
                    SqlCeEngine en = new SqlCeEngine(connectionString);
                    en.CreateDatabase();
                    MessageBox.Show("The database has been created!", "Success!", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    if (DialogResult == DialogResult.OK)
                    {
                        Close();
                    }
                }
            }
        }

         private void CancelB_Click(object sender, EventArgs e)
         {
             if (!File.Exists("PATH.txt") || File.ReadAllText("PATH.txt") == "")
             {
                    Application.Exit();
             }
             else
             {
                 Close();
             }
         }
    }
}
