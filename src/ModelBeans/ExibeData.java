package ModelBeans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jhame
 */
//classe responsavel por pegar a data atual do notebook e enviar para onde eu quiserrrrrrr
public class ExibeData {

    private String dateRetorno;
 
    //metodo para retornar a data completa dd/MM/YYYY
    public String dataCompleta() {
        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/YYYY");
        return dateRetorno = formatar.format(data);
    }
    public String datadia() {
        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("dd");
        return dateRetorno = formatar.format(data);
    }
    public String dataMes() {
        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("MM");
        return dateRetorno = formatar.format(data);
    }

}
