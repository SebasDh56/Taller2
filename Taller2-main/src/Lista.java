import java.util.ArrayList;
import java.util.List;

public class Lista {

    List<Paqueteria> serviEntrega;

    public Lista() {
        serviEntrega = new ArrayList<Paqueteria>();

    }

    public void adicionarElementos(Paqueteria r) throws Exception {
        if(serviEntrega.isEmpty())
            serviEntrega.add(r);
        else
            for (Paqueteria ra:serviEntrega)
                if (ra.getTracking()==r.getTracking())
                    throw new Exception( "Paquete ya existe ");
        serviEntrega.add(r);

    }
    public String listarPaquetes() {
        StringBuilder mensaje = new StringBuilder();
        for (Paqueteria paquete : serviEntrega) {
            mensaje.append(paquete).append("\n");
        }
        return mensaje.toString();
    }

    public int sumarTotal() {
        return serviEntrega.size();
    }

    public List<Paqueteria> getEntrega() {
        return serviEntrega;
    }

    public double sumarTotalPeso() {
        double totalPeso = 0;
        for (Paqueteria paquete : serviEntrega) {
            totalPeso += paquete.getPeso();
        }
        totalPeso = Math.round(totalPeso * 10.0) / 10.0;
        return totalPeso;
    }

    public double sumarTotalPesoCuidad(String ciudad) {
        double totalPeso = 0;
        for (Paqueteria paquete : serviEntrega) {
            if (paquete.getCiudadEntrega().equals(ciudad)) {
                totalPeso += paquete.getPeso();
            }
        }
        return totalPeso;
    }
}