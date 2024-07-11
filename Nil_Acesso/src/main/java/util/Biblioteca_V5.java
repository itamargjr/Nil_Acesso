package util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Normalizer;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

public class Biblioteca_V5 {

	/* com o static ao chamar o método na aplicação não precisa antes
	 * instanciar a biblioteca. Basta chamar Biblioteca.metodo() */
	 
	public static String msgerro;
	public static String tec_cm;
	
	//volta o id do usuário logado
	public static int idUsuLog() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (Integer) session.getAttribute("idusulogado");
	} 

	//volta o cracha do usuário logado
	public static String crachaUsuLog() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String) session.getAttribute("cracha");
	}
	
	// testa se a data é válida. True para uma data válida
	public static boolean dataValida(String date) { 

   	  DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
   	  df.setLenient (false); 
   	  try {
   	      df.parse (date);
   	        return true;
   	  	  } catch (ParseException ex) {
   	     	return false;
   	  }
	}
	
	public static int AnosParaDias(int anos) throws Exception{
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		Date dt1 = formato.parse(retornadatatual());
		Date dt2 = formato.parse(soma_anos(anos));
		
		return (int) calculadias(dt1, dt2);
	}
	
	// pega um nome e devolve só o prenome
	public static String primeiroNome(String nome) {

	    String pattern = "\\S+";
	    Pattern r = Pattern.compile(pattern);
	    Matcher m = r.matcher(nome);
	    if (m.find( )) 
	      return m.group(0);
	    else
	    return null;	
	}
	 
	// metodo que calcula a diferença entre datas, por dia
	public static long calculadias(Date dt1, Date dt2) throws ParseException{
		
		long dias_atraso = 0;

	    long dt = (dt2.getTime() - dt1.getTime()) + 3600000; 
	    dias_atraso = Math.round(dt / 86400000L); 
	    
	    return dias_atraso;		
	}

	// metodo que calcula a diferença entre horas, e devolve em minutos
	public static Double calculahoras (String horaA, String horaB) {

		horaA = horaA + ":00"; // segundos
		horaB = horaB + ":00";
		
        int sub = 0;
        int subHoras = 0;
        int subMinutos = 0;

        int segundos1 = (Integer.parseInt(horaA.substring(0, 2)) * 3600)
                + (Integer.parseInt(horaA.substring(3, 5)) * 60)
                + Integer.parseInt(horaA.substring(6));
        int segundos2 = (Integer.parseInt(horaB.substring(0, 2)) * 3600)
                + (Integer.parseInt(horaB.substring(3, 5)) * 60)
                + Integer.parseInt(horaB.substring(6));

        if (segundos1 > segundos2) {
            sub = segundos1 - segundos2;
        } else if (segundos2 > segundos1) {
            sub = segundos2 - segundos1;
        } else {
            sub = 0;
        }

        if (sub >= 3600) {
            subHoras = (sub - (sub % 3600)) / 3600;
            sub = sub - (subHoras * 3600);
        }

        if (sub >= 60) {
            subMinutos = (sub - (sub % 60)) / 60;
        }
        
        return (double) ((subHoras*60) + subMinutos);

    }	
	
	public static String valorToMoeda(Double valor){
		DecimalFormat df = new DecimalFormat("###,##0.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		return df.format(valor);
	}
	
	//recebe tempo em minutos e converte para horas no formato 00:00
	public static String minutoshoras(Double hrmin){ 

		int inteira = (int) (hrmin / 60);
		int resto = (int) (hrmin % 60);
		
		String hr, min;
		
		if (inteira<10)
			hr = "0" + inteira;
		else
			hr = ""+ inteira;
		if (resto<10)
			min = "0" + resto;
		else
			min = ""+ resto;
			
		return (hr + ":" + min);
		    		
	}
	
	public static long difDatasStr(String st_date, String end_date){
		
		long difDay  = 0;
		
		// SimpleDateFormat converte a string no formato data objeto
		SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date d1 = sdf.parse(st_date);
			Date d2 = sdf.parse(end_date);
			
			long diff = d2.getTime() - d1.getTime();

			TimeUnit time = TimeUnit.DAYS; 
	        difDay = time.convert(diff, TimeUnit.MILLISECONDS);
	        
	    }
		catch (ParseException e) {
			e.printStackTrace();
		}
		return  difDay;
	}
	
	
	//recebe tempo em horas (no formato 00:00) e converte para minutos
	public static Double horasminutos(String horas){  

	    return (Double.parseDouble(horas.substring(0, 2)) * 60)
	         + (Double.parseDouble(horas.substring(3, 5)));
	}
	
	/* somo 1 dia na data por problemas com o fuso horario, que diminui 1 dia na conversão GMT-3
	   Java interpreta a entrada de dados date como "dd/MM/yyyy 00:00" e diminui 3 horas do fuso 
	   voltando para 21:00 do dia anterior */
	public static String retornadatagmt(Date dt){
		
		String saida;
		
		if (dt==null)
			saida = "0";
		else{			
			Calendar c = Calendar.getInstance();
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			c.setTime(dt);
			c.add(Calendar.DATE, 1);  // dtini + 1
			saida = formatador.format(c.getTime());
		}	
		return saida;
	}
	
	// retorna a data atual como  String dd/mm/yyyy
	public static String retornadatatual(){
		
		Date date = new Date(); 
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		c.setTime(date);
		//c.add(Calendar.DATE, 1);  // dtini + 1
		return formatador.format(c.getTime());
	}
	// retorna a data atual menos o numero de dias. Retorna String dd/mm/yyyy
	public static String datatualmenosdias(Integer dias){
		
		Date date = new Date(); 
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		c.setTime(date);
		dias = dias * -1;
		c.add(Calendar.DATE, dias); 
		return formatador.format(c.getTime());
	}
	
	
	// retorna a data atual como  String dd/mm/yyyy hh:mm
	public static String datahoratual(){
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	// retorna a data hora atual para uso como nome de arquivo: ddMMyyHHmm
	public static String datahorarquivo(){
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
		Date date = new Date();
		return dateFormat.format(date);
	}

	// retorna só a hora atual como  String hh:mm
	public static String horatual(){
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date hora = new Date();
		return dateFormat.format(hora);
	}
	
	// retorna data atual formato yyyy-mm-dd
	public static String datatualEU(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	// soma anos a data (subtrai se o parâmetro for negativo)
	public static String soma_anos(int anos){ 

		Date d = new Date();
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy"); 

		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) + anos);
		d = c.getTime();
		return formatarDate.format(d);
	}

	// soma meses a data atual (subtrai se o parametro for negativo)
	public static String soma_meses(int meses){ 

		Date d = new Date();
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy"); 

		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + meses);
		d = c.getTime();
		return formatarDate.format(d);
	}

	// para dar reload na tela e atualizar campos
	public static void reload() throws IOException{ 
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		
	}
	
	// abre uma url em nova aba
	public static void abreurl(String url){
	try {
		  Desktop desktop = java.awt.Desktop.getDesktop();
		  URI oURL = new URI(url);
		  desktop.browse(oURL);
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
	
	//recebe hora e minuto e devolve a quantidade total em minutos
	public static Integer horaprasaldo(int hor, int min){
		Integer aux;
		aux = (hor * 60) + min ;
		return aux;
	}
	
	public static void showmessage(String msg) {
		 
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", msg);
        
        PrimeFaces.current().dialog().showMessageDynamic(message); //RequestContext.getCurrentInstance().showMessageInDialog(message); Versão antiga do primefaces
	}

	/* Solução para evitar duplicação de registro no recarregamento (F5) da página.
	 * Após gravar um registro, o usuário pode recarregar a página e o sistema grava novamente os mesmos dados.
	 * O ideal é direcionar para outra tela (limpando o método 'post'), dar refresh ou direcionar para a mesma página,
	 * mas isso faz com que a mensagem de gravação se perca.
	 * Essa chamada para mostrar a mensagem, salva ela em uma área (flash), dá reload na tela e a recoloca corretamente.
	 * Após o reload a página está em modo 'get' e não tem mais dados para gravar com o recarregamento. */	
	public static void erro(String msg) throws IOException{

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);// mantem a mensagem guardada em memória
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); // refresh na tela, limpando tudo e recolocando a mensagem
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	//idem, com tela azul de mensagem
	public static void mensagem(String msg) throws IOException{ 
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "")); // passa a mensagem
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);// mantem a mensagem guardada em memória
	
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); // refresh na tela, limpando tudo e recolocando a mensagem
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	//idem, com tela amarela de warning
	public static void aviso(String msg) throws IOException{ 
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "")); // passa a mensagem
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);// mantem a mensagem guardada em memória
	
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); // refresh na tela, limpando tudo e recolocando a mensagem
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	public static void MensagemInfo(String msg, String componente){
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(componente, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
	}
	
	public static void MensagemAviso(String msg, String componente){
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(componente, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, ""));
	}
	
	public static void MensagemErro(String msg, String componente){
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(componente, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
	}

	/* Para que a página seja atualizada automaticamente, ignorando cache. 
	 * Passo depois dela um parametro com um numero randomico. O browser atualiza sempre que vê um valor diferente. 
	 * Ex.:	return "principal.xhtml"+ Biblioteca.randomparam(); -- vai voltar "principal.xhtml?v=237"   */
	public static String randomparam() {
		Random random = new Random();
		int numero = random.nextInt(300);
		return "?v=" + numero;			
	}
	
	// padL("ABC",5) = "ABC  "
	public static String padL(String s, int n) { 
	     return String.format("%-" + n + "s", s);  
	}

	// padR("ABC",5) = "  ABC"
	public static String padR(String s, int n) { 
	    return String.format("%" + n + "s", s);  
	}
	
	// Método para testar se a string é nula ou vazia
	public static Boolean strnulo(String s){
		Boolean x = false;
		if ((s == null) || (s.trim().equals("")))
			x = true;	
		return x;
	}

	// Método para testar se a string com uma data é válida, nula ou vazia
	public static Boolean datanula(String s){
		Boolean x = false;
		if ((s == null) || (s.trim().equals("")))
			x = true;
		else	
			if (!dataValida(s))
				x = true;	
		return x;
	}

	// Método para testar se o inteiro é nulo, ou igual a zero
	public static Boolean intnulo(Integer s){
		Boolean x = false;
		if ((s == null) || (s.equals(0)))
			x = true;	
		return x;
	}
	
	// Método para testar se o double é nulo, ou igual a 0.0
	public static Boolean doubnulo(Double s){
		Boolean x = false;
		if ((s == null) || (s.equals(0.0)))
			x = true;	
		return x;
	}
	
	// Método para testar se a data é nula
	public static Boolean dtnula(Date s){
		Boolean x = false;
		if (s == null)
			x = true;	
		return x;
	}

	
	public static Double virgulaponto(String aux){
	// Recebe o string com virgula e troca por ponto, devolvendo Double
		String valor = aux.replace(",", "."); 
		Double sai = Double.parseDouble(valor);
		return sai;
	}
	
	public static String pontovirgula(Double aux){
	// Recebe o valor double e retorna string com a virgula trocada
		String qtde = String.valueOf(aux).replace(".", ",");
		return qtde;
	}
	
	public static String trocaAspas(String aux){
	// Recebe a string com aspas simples e troca para aspas duplas
		String valor = aux.replace("'", "\""); 
		return valor;
	}
	
	public static Boolean isnumeric (String aux){
	// valida se a string tem letra ou é só númerica	
		
		Boolean sai = true;
		
		aux = aux.trim();

		for (int i = 0; i < aux.length(); i++) {
			if (Character.isLetter(aux.charAt(i))==true) //isDigit
			{
				sai = false; // tem letra
				break;
			}
		}
		return sai;

    }
	// Retira os acentos, cedilha, til, crase e trema de uma string
	public static String limpaString(String texto){
		
		return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		
	}
	
	public static String tira_plic(String texto){
		return texto.replace("'", "");
	}
	
	/* Função para tirar links que tem no nome fantasia. Ex.: ROSA DAHLIA - @ROSADAHLIA 
	 * Recebe a razão social e o nome fantasia e devolve, se fantasia for diferente do 
	 * início da razão social, os dois concatenados. */
	public static String trataNomeCliFantasia(String razao, String fanta) {

		String sai = "";
		
		if (!strnulo(fanta)) {
			int pos = fanta.indexOf("- @");
			if (pos == -1)
				pos = fanta.indexOf("@");
			if (pos == -1)
			    pos = fanta.length(); 	
			fanta = fanta.substring(0, pos).toUpperCase();
		}

		sai = razao;

		if (!Biblioteca_V5.strnulo(fanta)) {
			if (fanta.length() > 22)
				fanta = fanta.substring(0, 22).trim(); 
			if (razao.length()> 22)
				razao = razao.substring(0, 22).trim(); 
			if (!razao.substring(0, fanta.length()).equals(fanta))  // Se o nome fantasia é diferente do começo da razao social
				sai = razao + " - " + fanta;
		}	
		
		return sai;
		
	}
	
	public static Double truncateduascasas(Double valor) {
		return Math.round(valor * 100)/100.0;
	}
	
	public static String DoubleToStr2c(Double valor) {
		DecimalFormat df = new DecimalFormat("0.00");
		
		return df.format(valor);
	}
	
	public static Double truncatetrescasas(Double valor) {
		return Math.round(valor * 1000)/1000.0;
	}
	
	public static String DoubleToStr3c(Double valor) {
		DecimalFormat df = new DecimalFormat("0.000");
		
		return df.format(valor);
	}
	
	// 3.0
	
	// Transforma uma data String dd/mm/yyyy em java.util.date
	public static java.util.Date StrToDate(String data) throws Exception {
		return new SimpleDateFormat("dd/MM/yyyy").parse(data);
	}
	
	// retorna a data informada menos o numero de dias. Retorna String dd/mm/yyyy
	public static String datamenosdias(String data, Integer dias) throws Exception {
		
		Date date = Biblioteca_V5.StrToDate(data);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		c.setTime(date);
		dias = dias * -1;
		c.add(Calendar.DATE, dias); 
		return formatador.format(c.getTime());
	}
	
	// retorna data informada no formato DD/MM/YYY, no formato  01/JUL/23
	public static String datadesc(String data) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yy");
		Date datadesc = Biblioteca_V5.StrToDate(data);
		return dateFormat.format(datadesc);
	}
	
	// Retorna double com duas casas formatado
	public static String DoubleToStr2cForm(Double valor) {
		DecimalFormat df = new DecimalFormat("###,###,##0.00");
		
		return df.format(valor);
	}
	
	// 4.0 - Sistema Pediddos
	
	public static Boolean eh_numero(String texto) {
		try {
		   Double.parseDouble(texto);
	       return true;
		} catch (NumberFormatException e) {	  
	       return false;
		}
	}
	
	// recebe uma data tipo Date e devolve String formatada
	public static String dateToStr (Date data) throws ParseException {
		String strData = data.toString();  
		Date dt = new SimpleDateFormat("yyyyMMdd").parse(strData);
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(dt);
		return dataFormatada;
	}
	
	public static String LimpaStringCaracterSpecial(String texto){
		//try{ // 20/02/2024 Itamar - Tirei o try para impedir excesso de log. Essa rotina era chamada pra testar strings nulas e isso ia pro log
		
		if (texto==null) {
			texto = "";
		} else {
			texto = texto.replaceAll("\\.","");
			texto = texto.replaceAll("\\-", "");
			texto = texto.replaceAll("\\)", "");
			texto = texto.replaceAll("\\(", "");
		}
					
		return texto;
		
		//}catch(Exception e){
		//	e.printStackTrace();
		//	return texto;
		//}
	}
	
	public static boolean IsCpf(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
		
		CPF = LimpaStringCaracterSpecial(CPF);
		
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;
        
        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0
        // (48 eh a posicao de '0' na tabela ASCII)
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
        }
	
}
