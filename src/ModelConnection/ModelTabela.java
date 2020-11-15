 

//classe responsavel por montar a tabela

//https://youtu.be/WJ3700ZIxBc?list=PLO38D8juJBEM-zrARo_d3yyUmRZtlFTDr

package ModelConnection;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jhame
 */
public class ModelTabela extends AbstractTableModel {

    private ArrayList linhas = null; //
    private String[] colunas = null; //

    public ModelTabela(ArrayList lin, String[] col) {
        setLinhas(lin);
        setColunas(col);
    }
    /**
     * @return the linhas
     */
    public ArrayList getLinhas() {
        return linhas;
    }

    /**
     * @param dados the linhas to set
     */
    public void setLinhas(ArrayList dados) {
        this.linhas = dados;
    }

    /**
     * @return the colunas
     */
    public String[] getColunas() {
        return colunas;
    }

    /**
     * @param colunas the colunas to set
     */
    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }

    //contas as colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //conta as linhas
    public int getRowCount() {
        return linhas.size();
    }

    //pegar o valor do nome das colunas
    public String getColunmName(int numColuna) {
        return colunas[numColuna];
    }

    //poder montar a tabela
    public Object getValueAt(int numLin, int numCol) {
        Object[] linha = (Object[])getLinhas().get(numLin);
        return linha[numCol];
    }

    
}
