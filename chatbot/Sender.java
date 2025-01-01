import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class Sender implements ActionListener{
    static JFrame f=new JFrame();
    JTextField text;
    static JPanel c;
    static Box vertical=Box.createVerticalBox();

    static DataOutputStream dout;
    Sender() {
        f.setLayout(null);

        JPanel p=new JPanel();
        p.setBackground(new Color(7,94,84));
        p.setBounds(0,0,500,80);
        p.setLayout(null);
        f.add(p);

        ImageIcon i=new ImageIcon(ClassLoader.getSystemResource("icons/back icon.png"));
        Image i1=i.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i2=new ImageIcon(i1);
        JLabel back=new JLabel(i2);
        back.setBounds(5,25,25,25);
        p.add(back);

        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });

        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("icons/profile.png"));
        Image i4=i3.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);
        ImageIcon i5=new ImageIcon(i4);
        JLabel profile=new JLabel(i5);
        profile.setBounds(40,10,60,60);
        p.add(profile);

        
        ImageIcon i9=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i10=i9.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i11=new ImageIcon(i10);
        JLabel video=new JLabel(i11);
        video.setBounds(350,20,30,30);
        p.add(video);

        ImageIcon i6=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i7=i6.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i8=new ImageIcon(i7);
        JLabel call=new JLabel(i8);
        call.setBounds(400,20,30,30);
        p.add(call);

        ImageIcon i12=new ImageIcon(ClassLoader.getSystemResource("icons/3dots.png"));
        Image i13=i12.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i14=new ImageIcon(i13);
        JLabel more=new JLabel(i14);
        more.setBounds(450,20,10,25);
        p.add(more);

        JLabel name=new JLabel("Kutty");
        name.setBounds(125,19,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p.add(name);

        JLabel status=new JLabel("Active Now");
        status.setBounds(125,40,100,20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p.add(status);

        c=new JPanel();
      
        c.setBounds(5,85,490,450);
        f.add(c);

        text=new JTextField();
        
         text.setBounds(5,540,380,55);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);
       

        JButton send=new JButton("Send");
       
        send.setBounds(390,540,100,55);
        send.setBackground(new Color(7,94,84));
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        send.setForeground(Color.WHITE);
        f.add(send);
        send.addActionListener(this);

        f.setSize(500, 600);
       //f.setLocation(300,200);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.LIGHT_GRAY);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        try{
        String out=text.getText();
        JLabel output=new JLabel(out);
        JPanel p2=FormatLabel(out);
       

        c.setLayout(new BorderLayout());
        JPanel right=new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        c.add(vertical,BorderLayout.PAGE_START);

        dout.writeUTF(out);

        text.setText("");
        f.repaint();
        f.invalidate();
        f.validate();
        }
        catch(Exception l){
            l.printStackTrace();
        }
    }
    public static JPanel FormatLabel(String out){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output=new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);


        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;

    }

    public static void main(String[] args) {
        new Sender();
        try {
               
            ServerSocket skt=new ServerSocket(6001);
            while(true){
                Socket s=skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());
                dout=new DataOutputStream(s.getOutputStream());
                //dout=new DataOutputStream(s.getOutputStream());

                while(true){
                    String msg=din.readUTF();
                    JPanel panel=FormatLabel(msg);

                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }
        catch(Exception e){
           // e.printStackTrace();
           e.printStackTrace();
        }
    }
}
