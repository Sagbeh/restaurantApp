import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * Created by Maia on 5/3/2017.
 */
public class restaurantForm extends JFrame implements WindowListener {

    //DefaultTableModel used for JTable
    public  DefaultTableModel dataModel = new DefaultTableModel();
    private JButton addButton;
    private JTextField textField1;
    public  JTable restaurantsTable;
    private JPanel rootPanel;

    protected restaurantForm() {
        setContentPane(rootPanel);
        pack();
        setVisible(true);

        //addWindowListener listens for when the window is opened and runs method(below)
        addWindowListener(this);

        //Add button method
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Adds information from the table to the database.
                restaurantDB.addRestaurantToDB(textField1.getText());

                //create new model - not sure if this is needed everytime?
                DefaultTableModel dataModel = new DefaultTableModel(1, 1);

                //Uses fetchDB method to return an arraylist of the database.
                ArrayList<String> saved = restaurantDB.fetchDB();

                restaurantsTable.setModel(dataModel);

                //For loop adds information in arrayList to JTable
                for (String rest : saved){
                    Object [] row = {rest};
                    dataModel.addRow(row);
                }
            }
        });
    }

    public static void main(String[] args) {
        //initiates the form
        restaurantForm gui = new restaurantForm();
    }


    //Loads saved information from the database to the table when the window is initially opened
    @Override
    public void windowOpened(WindowEvent e) {

        //create new model - not sure if this is needed everytime?
        DefaultTableModel dataModel = new DefaultTableModel(1, 1);

        //Uses loadSavedRestaurants method to return an arraylist of the previously saved database.
        ArrayList<String> saved = restaurantDB.loadSavedRestaurants();

        restaurantsTable.setModel(dataModel);

        //For loop adds information in arrayList to JTable
        for (String rest : saved){
            Object [] row = {rest};
            dataModel.addRow(row);
        }
    }
    @Override
    public void windowClosing(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) { }
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
