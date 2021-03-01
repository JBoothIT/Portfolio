namespace Engraver_s_Friend
{
    partial class CreateFile
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(CreateFile));
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.BrowseB = new System.Windows.Forms.Button();
            this.CancelB = new System.Windows.Forms.Button();
            this.CreateB = new System.Windows.Forms.Button();
            this.ImportB = new System.Windows.Forms.Button();
            this.FileBox = new System.Windows.Forms.TextBox();
            this.PATHBox = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.BrowseB);
            this.groupBox1.Controls.Add(this.CancelB);
            this.groupBox1.Controls.Add(this.CreateB);
            this.groupBox1.Controls.Add(this.ImportB);
            this.groupBox1.Controls.Add(this.FileBox);
            this.groupBox1.Controls.Add(this.PATHBox);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(17, 16);
            this.groupBox1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Padding = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.groupBox1.Size = new System.Drawing.Size(440, 124);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Create File";
            // 
            // BrowseB
            // 
            this.BrowseB.Location = new System.Drawing.Point(399, 18);
            this.BrowseB.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.BrowseB.Name = "BrowseB";
            this.BrowseB.Size = new System.Drawing.Size(33, 28);
            this.BrowseB.TabIndex = 7;
            this.BrowseB.Text = "...";
            this.BrowseB.UseVisualStyleBackColor = true;
            this.BrowseB.Click += new System.EventHandler(this.button4_Click);
            // 
            // CancelB
            // 
            this.CancelB.Location = new System.Drawing.Point(289, 86);
            this.CancelB.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.CancelB.Name = "CancelB";
            this.CancelB.Size = new System.Drawing.Size(100, 28);
            this.CancelB.TabIndex = 6;
            this.CancelB.Text = "Cancel";
            this.CancelB.UseVisualStyleBackColor = true;
            this.CancelB.Click += new System.EventHandler(this.CancelB_Click);
            // 
            // CreateB
            // 
            this.CreateB.Location = new System.Drawing.Point(52, 89);
            this.CreateB.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.CreateB.Name = "CreateB";
            this.CreateB.Size = new System.Drawing.Size(100, 28);
            this.CreateB.TabIndex = 5;
            this.CreateB.Text = "Create";
            this.CreateB.UseVisualStyleBackColor = true;
            this.CreateB.Click += new System.EventHandler(this.CreateB_Click);
            // 
            // ImportB
            // 
            this.ImportB.Location = new System.Drawing.Point(171, 87);
            this.ImportB.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.ImportB.Name = "ImportB";
            this.ImportB.Size = new System.Drawing.Size(100, 28);
            this.ImportB.TabIndex = 4;
            this.ImportB.Text = "Import";
            this.ImportB.UseVisualStyleBackColor = true;
            this.ImportB.Click += new System.EventHandler(this.button1_Click);
            // 
            // FileBox
            // 
            this.FileBox.Location = new System.Drawing.Point(111, 54);
            this.FileBox.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.FileBox.Name = "FileBox";
            this.FileBox.Size = new System.Drawing.Size(279, 22);
            this.FileBox.TabIndex = 3;
            // 
            // PATHBox
            // 
            this.PATHBox.Location = new System.Drawing.Point(111, 21);
            this.PATHBox.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.PATHBox.Name = "PATHBox";
            this.PATHBox.Size = new System.Drawing.Size(279, 22);
            this.PATHBox.TabIndex = 2;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(9, 58);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(75, 17);
            this.label2.TabIndex = 1;
            this.label2.Text = "File Name:";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(9, 25);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(92, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "File Location:";
            // 
            // CreateFile
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(473, 155);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "CreateFile";
            this.Text = "Startup";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.TextBox FileBox;
        private System.Windows.Forms.TextBox PATHBox;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button BrowseB;
        private System.Windows.Forms.Button CancelB;
        private System.Windows.Forms.Button CreateB;
        private System.Windows.Forms.Button ImportB;
    }
}