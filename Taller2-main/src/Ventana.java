import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Ventana {
    private JPanel Ventana;
    private JTabbedPane tabbedPane1;
    private JSpinner spinner1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JButton agregarButton;
    private JTextField textField2;
    private JButton totalPaquetesButton;
    private JComboBox comboBox3;
    private JButton totalPesoButton;
    private JComboBox comboBox4;
    private JButton totalPesoPorCiudadButton1;
    private JList list1;
    private JButton modificarButton;
    private JButton ordenarButton;
    private JList list2;
    private JTextField textField3;
    private JButton buscarButton;
    private JTextArea textArea1;
    private JList list3;
    public Lista paquetes = new Lista();

    public Ventana() {
        quemarDatos();
        LlenarJlist();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    paquetes.adicionarElementos(new Paqueteria(Integer.parseInt(spinner1.getValue().toString()),
                            Double.parseDouble(textField2.getText()),
                            comboBox1.getSelectedItem().toString(),
                            comboBox2.getSelectedItem().toString(),
                            textField1.getText(),
                            "Receptado"));

                    JOptionPane.showMessageDialog(null, "Paquete agregado");
                    limpiarDatos();
                    LlenarJlist();
                    System.out.println(paquetes.listarPaquetes());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        });

        totalPaquetesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "El total de paquetes es: " + paquetes.sumarTotal());
            }
        });


        totalPesoPorCiudadButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCity = comboBox3.getSelectedItem().toString();
                System.out.println("Ciudad seleccionada: " + selectedCity); // Mensaje de depuración
                double totalWeight = paquetes.sumarTotalPesoCuidad(selectedCity);
                JOptionPane.showMessageDialog(null, "El total de peso de la ciudad " + selectedCity + " es: " + totalWeight);
            }
        });


        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    int indice = list1.getSelectedIndex();
                    Paqueteria pa = paquetes.getEntrega().get(indice);
                    spinner1.setValue(pa.getTracking());
                    comboBox1.setSelectedItem(pa.getCedulaReceptor());
                    comboBox2.setSelectedItem(pa.getCiudadEntrega());
                    textField1.setText(" " + pa.getCedulaReceptor());
                    textField2.setText(" " + pa.getPeso());
                }
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    int indice = list1.getSelectedIndex();
                    Paqueteria pa = paquetes.getEntrega().get(indice);
                    pa.setTracking(Integer.parseInt(spinner1.getValue().toString()));
                    pa.setPeso(Double.parseDouble(textField2.getText()));
                    pa.setCiudadEntrega(comboBox1.getSelectedItem().toString());
                    pa.setCiudadRecepcion(comboBox2.getSelectedItem().toString());
                    pa.setCedulaReceptor(textField1.getText());

                    LlenarJlist();
                    limpiarDatos();
                    JOptionPane.showMessageDialog(null, "Paquete modificado");
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un paquete para modificar");
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tracking = Integer.parseInt(textField3.getText());
                busquedaBinaria(tracking);
            }
        });


        ordenarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarBurbuja();
            }
        });
        totalPesoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "El total de peso es: " + paquetes.sumarTotalPeso());
            }
        });
    }

    public void quemarDatos() {
        try {
            paquetes.adicionarElementos(new Paqueteria(15, 140.47, "Quito", "Cuenca", "1747651043","Receptado"));
            paquetes.adicionarElementos(new Paqueteria(10, 50.00, "Quito", "Cuenca", "1045310245","Receptado"));
            paquetes.adicionarElementos(new Paqueteria(19, 47.38, "Guayaquil", "Quito", "1704679137","Receptado"));
            paquetes.adicionarElementos(new Paqueteria(20, 100.00, "Quito", "Cuenca", "1700463105","Receptado"));
            paquetes.adicionarElementos(new Paqueteria(06, 101.00, "Cuenca", "Quito", "1750958567","Receptado"));
            paquetes.adicionarElementos(new Paqueteria(21, 20.00, "Cuenca", "Quito", "1747958567","Receptado"));
            paquetes.adicionarElementos(new Paqueteria(17, 10.78, "Cuenca", "Quito", "1755447633","Receptado"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LlenarJlist() {
        DefaultListModel<String> lista = new DefaultListModel<>();
        list1.setModel(lista); // Limpiar lista antes de agregar nuevos elementos
        for (Paqueteria pa : paquetes.getEntrega()) {
            lista.addElement(pa.toString());
        }
    }

    public void LlenarJlist2() {
        DefaultListModel<String> lista = new DefaultListModel<>();
        list2.setModel(lista); // Limpiar lista antes de agregar nuevos elementos
        for (Paqueteria pa : paquetes.getEntrega()) {
            lista.addElement(pa.toString());
        }
    }


    public void ordenarBurbuja(){
        List<Paqueteria> entregas = paquetes.getEntrega();
        LlenarJlist2(); // Llenar list3 antes de ordenar para que se actualice con los datos originales
        int n = entregas.size();
        Paqueteria aux;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (entregas.get(j).getPeso() > entregas.get(j + 1).getPeso()) {
                    aux = entregas.get(j);
                    entregas.set(j, entregas.get(j + 1));
                    entregas.set(j + 1, aux);
                }
            }
        }
        LlenarJlist2();
    }
    public void busquedaBinaria(int tracking) {
        List<Paqueteria> entregas = paquetes.getEntrega();
        int inicio = 1;
        int fin = entregas.size() - 1;
        int indice = -1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Paqueteria paquete = entregas.get(medio);
            if (paquete.getTracking() == tracking) {
                indice = medio;
                break;
            } else if (paquete.getTracking() < tracking) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        if (indice != -1) {
            Paqueteria paqueteEncontrado = entregas.get(indice);
            textArea1.setText(paqueteEncontrado.toString());
        } else {
            textArea1.setText("Paquete no encontrado");
        }
    }


    public void limpiarDatos(){
        spinner1.setValue(0);
        spinner1.setValue(0);
        comboBox1.setSelectedIndex(0);
        comboBox2.setSelectedIndex(0);
        comboBox3.setSelectedIndex(0);
        comboBox4.setSelectedIndex(0);
        textField1.setText("");
        textField2.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
