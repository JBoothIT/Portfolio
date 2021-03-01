namespace Engraver_s_Friend
{
    partial class ImportForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ImportForm));
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.SSCancel = new System.Windows.Forms.Button();
            this.SSImport = new System.Windows.Forms.Button();
            this.SSBrowse = new System.Windows.Forms.Button();
            this.SSFILE = new System.Windows.Forms.TextBox();
            this.SSPATH = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.DBCancel = new System.Windows.Forms.Button();
            this.DBImport = new System.Windows.Forms.Button();
            this.DBBrowse = new System.Windows.Forms.Button();
            this.DBFILE = new System.Windows.Forms.TextBox();
            this.DBPATH = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.SSCancel);
            this.groupBox1.Controls.Add(this.SSImport);
            this.groupBox1.Controls.Add(this.SSBrowse);
            this.groupBox1.Controls.Add(this.SSFILE);
            this.groupBox1.Controls.Add(this.SSPATH);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(15, 15);
            this.groupBox1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox1.Size = new System.Drawing.Size(443, 137);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Import Spreadsheet";
            // 
            // SSCancel
            // 
            this.SSCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.SSCancel.Location = new System.Drawing.Point(260, 87);
            this.SSCancel.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.SSCancel.Name = "SSCancel";
            this.SSCancel.Size = new System.Drawing.Size(115, 28);
            this.SSCancel.TabIndex = 6;
            this.SSCancel.Text = "Cancel";
            this.SSCancel.UseVisualStyleBackColor = true;
            // 
            // SSImport
            // 
            this.SSImport.Location = new System.Drawing.Point(68, 87);
            this.SSImport.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.SSImport.Name = "SSImport";
            this.SSImport.Size = new System.Drawing.Size(115, 28);
            this.SSImport.TabIndex = 5;
            this.SSImport.Text = "Import";
            this.SSImport.UseVisualStyleBackColor = true;
            this.SSImport.Click += new System.EventHandler(this.SSImport_Click);
            // 
            // SSBrowse
            // 
            this.SSBrowse.Location = new System.Drawing.Point(389, 23);
            this.SSBrowse.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.SSBrowse.Name = "SSBrowse";
            this.SSBrowse.Size = new System.Drawing.Size(41, 28);
            this.SSBrowse.TabIndex = 4;
            this.SSBrowse.Text = "...";
            this.SSBrowse.UseVisualStyleBackColor = true;
            this.SSBrowse.Click += new System.EventHandler(this.SSBrowse_Click);
            // 
            // SSFILE
            // 
            this.SSFILE.Location = new System.Drawing.Point(108, 55);
            this.SSFILE.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.SSFILE.Name = "SSFILE";
            this.SSFILE.Size = new System.Drawing.Size(272, 22);
            this.SSFILE.TabIndex = 3;
            // 
            // SSPATH
            // 
            this.SSPATH.Location = new System.Drawing.Point(108, 23);
            this.SSPATH.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.SSPATH.Name = "SSPATH";
            this.SSPATH.Size = new System.Drawing.Size(272, 22);
            this.SSPATH.TabIndex = 2;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(11, 59);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(75, 17);
            this.label2.TabIndex = 1;
            this.label2.Text = "File Name:";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(11, 27);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(92, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "File Location:";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.DBCancel);
            this.groupBox2.Controls.Add(this.DBImport);
            this.groupBox2.Controls.Add(this.DBBrowse);
            this.groupBox2.Controls.Add(this.DBFILE);
            this.groupBox2.Controls.Add(this.DBPATH);
            this.groupBox2.Controls.Add(this.label3);
            this.groupBox2.Controls.Add(this.label4);
            this.groupBox2.Location = new System.Drawing.Point(15, 159);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox2.Size = new System.Drawing.Size(443, 137);
            this.groupBox2.TabIndex = 7;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Import Database";
            // 
            // DBCancel
            // 
            this.DBCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            this.DBCancel.Location = new System.Drawing.Point(260, 87);
            this.DBCancel.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.DBCancel.Name = "DBCancel";
            this.DBCancel.Size = new System.Drawing.Size(115, 28);
            this.DBCancel.TabIndex = 6;
            this.DBCancel.Text = "Cancel";
            this.DBCancel.UseVisualStyleBackColor = true;
            // 
            // DBImport
            // 
            this.DBImport.Location = new System.Drawing.Point(68, 87);
            this.DBImport.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.DBImport.Name = "DBImport";
            this.DBImport.Size = new System.Drawing.Size(115, 28);
            this.DBImport.TabIndex = 5;
            this.DBImport.Text = "Import";
            this.DBImport.UseVisualStyleBackColor = true;
            // 
            // DBBrowse
            // 
            this.DBBrowse.Location = new System.Drawing.Point(389, 23);
            this.DBBrowse.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.DBBrowse.Name = "DBBrowse";
            this.DBBrowse.Size = new System.Drawing.Size(41, 28);
            this.DBBrowse.TabIndex = 4;
            this.DBBrowse.Text = "...";
            this.DBBrowse.UseVisualStyleBackColor = true;
            this.DBBrowse.Click += new System.EventHandler(this.DBBrowse_Click);
            // 
            // DBFILE
            // 
            this.DBFILE.Location = new System.Drawing.Point(108, 55);
            this.DBFILE.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.DBFILE.Name = "DBFILE";
            this.DBFILE.Size = new System.Drawing.Size(272, 22);
            this.DBFILE.TabIndex = 3;
            // 
            // DBPATH
            // 
            this.DBPATH.Location = new System.Drawing.Point(108, 23);
            this.DBPATH.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.DBPATH.Name = "DBPATH";
            this.DBPATH.Size = new System.Drawing.Size(272, 22);
            this.DBPATH.TabIndex = 2;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(11, 59);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(75, 17);
            this.label3.TabIndex = 1;
            this.label3.Text = "File Name:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(11, 27);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(92, 17);
            this.label4.TabIndex = 0;
            this.label4.Text = "File Location:";
            // 
            // ImportForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(473, 309);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "ImportForm";
            this.Text = "Import";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button SSCancel;
        private System.Windows.Forms.Button SSImport;
        private System.Windows.Forms.Button SSBrowse;
        private System.Windows.Forms.TextBox SSFILE;
        private System.Windows.Forms.TextBox SSPATH;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button DBCancel;
        private System.Windows.Forms.Button DBImport;
        private System.Windows.Forms.Button DBBrowse;
        private System.Windows.Forms.TextBox DBFILE;
        private System.Windows.Forms.TextBox DBPATH;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
    }
}