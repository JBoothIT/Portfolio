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
    */
    public partial class ImportForm : Form
    {
        public ImportForm()
        {
            InitializeComponent();
        }

        private void SSImport_Click(object sender, EventArgs e)
        {

        }

        private void SSBrowse_Click(object sender, EventArgs e)
        {
            try
            {
                FolderBrowserDialog f1 = new FolderBrowserDialog();
                if (f1.ShowDialog() == DialogResult.OK)
                {
                    SSPATH.Text = f1.SelectedPath;
                }
            }
            catch
            {

            }
        }

        private void DBBrowse_Click(object sender, EventArgs e)
        {
            try
            {
                FolderBrowserDialog f2 = new FolderBrowserDialog();
                if (f2.ShowDialog() == DialogResult.OK)
                {
                    DBPATH.Text = f2.SelectedPath;
                }
            }
            catch
            {

            }
        }
    }
}
