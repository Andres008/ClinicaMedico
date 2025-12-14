package ec.com.model.modulos.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelUtil {
	/**
	 * Verifica si una cadena es igual a null o tiene longitud igual a cero (0).
	 * 
	 * @param cadena Cadena que va a verificarse.
	 * @return
	 */
	public static boolean isEmpty(String cadena) {
		if (cadena == null || cadena.length() == 0)
			return true;
		return false;
	}

	/**
	 * MÉTODO PARA OBTENER LA EXTENSION DE UN ARCHIVO
	 * 
	 * @param String nombreArchivo
	 */
	public static String obtenerExtensionArchivo(String nombre) {
		String extension = "";
		String arreglo[] = nombre.split("\\.");
		extension = arreglo[arreglo.length - 1];
		return "." + extension;
	}

	public static Date sumarRestarMes(Date fecha, int numeroMeses) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.MONTH, numeroMeses);
		return calendar.getTime();
	}

	// Este es el método calcularEdad que se manda a llamar en el main
	public static Integer calcularEdad(Date fechaNac) {
		try {
		} catch (Exception ex) {
			System.out.println("Error:" + ex);
		}
		Calendar fechaNacimiento = Calendar.getInstance();
		// Se crea un objeto con la fecha actual
		Calendar fechaActual = Calendar.getInstance();
		// Se asigna la fecha recibida a la fecha de nacimiento.
		fechaNacimiento.setTime(fechaNac);
		// Se restan la fecha actual y la fecha de nacimiento
		int año = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
		int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
		int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
		// Se ajusta el año dependiendo el mes y el día
		if (mes < 0 || (mes == 0 && dia < 0)) {
			año--;
		}
		// Regresa la edad en base a la fecha de nacimiento
		return año;
	}

	/**
	 * Devuelve el valor del anio actual.
	 * 
	 * @return valor correspondiente al anio actual.
	 */
	public static int getAnioActual() {
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy");
		int anioActual = Integer.parseInt(formato.format(fechaActual));
		return anioActual;
	}

	/**
	 * Devuelve la parte del anio de una fecha.
	 * 
	 * @param fecha La fecha a procesar.
	 * @return
	 */
	public static int getAnio(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy");
		int anioActual = Integer.parseInt(formato.format(fecha));
		return anioActual;
	}

	/**
	 * Devuelve la parte del anio de una fecha.
	 * 
	 * @param fecha La fecha a procesar.
	 * @return
	 */
	public static int getMes(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("MM");
		int anioActual = Integer.parseInt(formato.format(fecha));
		return anioActual;
	}

	/**
	 * Devuelve la hora.
	 * 
	 * @param fecha La fecha a procesar.
	 * @return
	 */
	public static int getHora(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("HH");
		int anioActual = Integer.parseInt(formato.format(fecha));
		return anioActual;
	}

	/**
	 * Devuelve los minutos.
	 * 
	 * @param fecha La fecha a procesar.
	 * @return
	 */
	public static int getMinutos(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("mm");
		int anioActual = Integer.parseInt(formato.format(fecha));
		return anioActual;
	}

	/**
	 * Devuelve la parte del anio de una fecha.
	 * 
	 * @param fecha La fecha a procesar.
	 * @return
	 */
	public static int getDia(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd");
		int anioActual = Integer.parseInt(formato.format(fecha));
		return anioActual;
	}

	/**
	 * Devuelve el valor del mes actual.
	 * 
	 * @return valor correspondiente al mes actual.
	 */
	public static int getMesActual() {
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("MM");
		int mesActual = Integer.parseInt(formato.format(fechaActual));
		return mesActual;
	}

	/**
	 * Valida si es correcta la dirección de correo electrónica dada.
	 * 
	 * @param email
	 * @return true si es correcta o false si no lo es.
	 * @throws Exception
	 */
	public static void esEmailCorrecto(String email) throws Exception {

		// Patrón para validar el email
		Pattern pattern = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		Matcher mather = pattern.matcher(email);

		if (!mather.find())
			throw new Exception("Error en formato de email");
	}

	/**
	 * Suma o resta dias a una fecha.
	 * 
	 * @param Fecha  original.
	 * @param numero de dias a ser sumados.
	 * @return fecha sumada el numero de dias
	 */
	public static Date getSumarDias(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias); // numero de dÃ­as a aÃ±adir, o restar en caso de dÃ­as<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos dÃ­as aÃ±adidos
	}

	/**
	 * Suma o resta meses a una fecha.
	 * 
	 * @param Fecha  original.
	 * @param numero de dias a ser sumados.
	 * @return fecha sumada el numero de dias
	 */
	public static Date getSumarMeses(Date fecha, int mes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.MONTH, mes); // numero de dÃ­as a aÃ±adir, o restar en caso de dÃ­as<0

		return calendar.getTime(); // Devuelve el objeto Date con los nuevos dÃ­as aÃ±adidos
	}

	// Diferencias entre dos fechas
	// @param fechaInicial La fecha de inicio
	// @param fechaFinal La fecha de fin
	// @return Retorna el numero de dias entre dos fechas
	public static int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString = df.format(fechaInicial);
		try {
			fechaInicial = df.parse(fechaInicioString);
		} catch (Exception ex) {
		}

		String fechaFinalString = df.format(fechaFinal);
		try {
			fechaFinal = df.parse(fechaFinalString);
		} catch (Exception ex) {
		}
		long fechaInicialMs = fechaInicial.getTime();
		long fechaFinalMs = fechaFinal.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ((int) dias);
	}

	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public static String fecha(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		return formato.format(fecha);
	}

	/**
	 * Devuelve el dia numerico.
	 * 
	 * @param fecha La fecha a procesar.
	 * @return
	 */
	public static String getDayAlfaNumerico(Date d) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		String diaAlfanumerico = "";

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			diaAlfanumerico = "Domingo";
			break;
		case 2:
			diaAlfanumerico = "Lunes";
			break;
		case 3:
			diaAlfanumerico = "Martes";
			break;
		case 4:
			diaAlfanumerico = "Miercoles";
			break;
		case 5:
			diaAlfanumerico = "Jueves";
			break;
		case 6:
			diaAlfanumerico = "Viernes";
			break;
		case 7:
			diaAlfanumerico = "Sabado";
			break;
		default:
			break;
		}
		return diaAlfanumerico;
	}

	/**
	 * Devuelve el mes alfanumerico.
	 * 
	 * @param fecha La fecha a procesar.
	 * @return
	 */
	public static String getMesAlfanumerico(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("MM");
		int mesActual = Integer.parseInt(formato.format(fecha));
		String mesAlfanumerico = "";
		switch (mesActual) {
		case 1:
			mesAlfanumerico = "Enero";
			break;
		case 2:
			mesAlfanumerico = "Febrero";
			break;
		case 3:
			mesAlfanumerico = "Marzo";
			break;
		case 4:
			mesAlfanumerico = "Abril";
			break;
		case 5:
			mesAlfanumerico = "Mayo";
			break;
		case 6:
			mesAlfanumerico = "Junio";
			break;
		case 7:
			mesAlfanumerico = "Julio";
			break;
		case 8:
			mesAlfanumerico = "Agosto";
			break;
		case 9:
			mesAlfanumerico = "Septiembre";
			break;
		case 10:
			mesAlfanumerico = "Octubre";
			break;
		case 11:
			mesAlfanumerico = "Noviembre";
			break;
		case 12:
			mesAlfanumerico = "Diciembre";
			break;
		default:
			break;
		}
		return mesAlfanumerico;
	}

	/**
	 * 
	 * @param fechaHoraDesde
	 * @param fechaHoraHasta
	 * @return
	 */
	public static BigDecimal diferenciaHoras(Date fechaHoraDesde, Date fechaHoraHasta) {
		long milliseconds = fechaHoraHasta.getTime() - fechaHoraDesde.getTime();
		double minutes = (double) ((milliseconds / (1000 * 60)) % 60);
		double hours = (double) ((milliseconds / (1000 * 60 * 60)) % 24);
		return new BigDecimal((hours + (minutes / 60)));
	}

	/**
	 * 
	 * @param inputstream
	 * @param nombreArchivo
	 * @param rutaDestino
	 * @param formatArchivo
	 * @return
	 * @throws Exception
	 */
	public static String guardarArchivo(InputStream inputstream, String nombreArchivo, String rutaDestino,
			String formatArchivo) throws Exception {
		File fRuta = new File(rutaDestino);
		if (!fRuta.exists()) {
			File directorio = new File(rutaDestino);
			directorio.mkdirs();
			rutaDestino = directorio.toString() + "/";
		}
		String nombreArchivoCompleto;
		if (rutaDestino == null || rutaDestino.length() == 0)
			throw new Exception("Error en parÃ¡metro RUTA_ARCHIVOS_ANEXOS, contacte al Administrador.");
		if (inputstream != null) {
			nombreArchivoCompleto = rutaDestino + nombreArchivo + formatArchivo;
			FileOutputStream fileOutput = new FileOutputStream(nombreArchivoCompleto);
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);
			// Bucle para leer de un archivo y escribir en el otro.
			byte[] array = new byte[1000];
			int leidos = inputstream.read(array);
			while (leidos > 0) {
				bufferedOutput.write(array, 0, leidos);
				leidos = inputstream.read(array);
			}
			// Cierre de los ficheros
			inputstream.close();
			bufferedOutput.close();
		} else
			throw new Exception("Error guardando archivo: no existe el flujo indicado (inputStream)");
		return nombreArchivoCompleto;
	}

	/**
	 * 
	 * @param clave
	 * @return
	 * @throws Exception
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(String clave) throws Exception {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(clave.getBytes("UTF-8"), 0, clave.length());
			byte[] bt = md.digest();
			BigInteger bi = new BigInteger(1, bt);
			String md5 = bi.toString(16);
			return md5.toUpperCase();
		} catch (Exception e) {
			throw new Exception("Error al codificar Clave.");
		}
	}

	public static String cambiarMayusculas(String cadena) {
		return cadena.toUpperCase();
	}

	public static String cambiarMinusculas(String cadena) {
		return cadena.toLowerCase();
	}

	public static String nombreEstadoByInicial(String estado) {
		return (estado.equals("A")) ? "ACTIVO" : "INACTIVO";
	}

	// Función principal que hace uso de las anteriores funciones
	/*
	 * public static boolean verificarCedulaEcuador(String idCard) throws Exception
	 * { if (stringLength(idCard, 10)) { String[] data = idCard.split(""); byte
	 * verifier = Byte.parseByte(data[0] + data[1]); byte[] digits = new byte[9];
	 * for (byte i = 0; i < 9; i++) digits[i] = Byte.parseByte(data[i]); if
	 * (verifier >= 1 && verifier <= 24) { verifier = digits[2]; if (verifier <= 6)
	 * { if (sumDigits(digits) == Byte.parseByte(data[9])) return true; } } } else
	 * throw new Exception("Número digitos cédula invalido."); throw new
	 * Exception("Cédula invalidad"); }
	 */

	public static boolean verificarCedulaEcuador(String idCard) throws Exception {
		int suma = 0;
		if (idCard.length() != 10) {
			throw new Exception("Dígitos de la cedula incorrecto.");

		} else {
			int a[] = new int[idCard.length() / 2];
			int b[] = new int[(idCard.length() / 2)];
			int c = 0;
			int d = 1;
			for (int i = 0; i < idCard.length() / 2; i++) {
				a[i] = Integer.parseInt(String.valueOf(idCard.charAt(c)));
				c = c + 2;
				if (i < (idCard.length() / 2) - 1) {
					b[i] = Integer.parseInt(String.valueOf(idCard.charAt(d)));
					d = d + 2;
				}
			}

			for (int i = 0; i < a.length; i++) {
				a[i] = a[i] * 2;
				if (a[i] > 9) {
					a[i] = a[i] - 9;
				}
				suma = suma + a[i] + b[i];
			}
			int aux = suma / 10;
			int dec = (aux + 1) * 10;
			if ((dec - suma) == Integer.parseInt(String.valueOf(idCard.charAt(idCard.length() - 1))))
				return true;
			else if (suma % 10 == 0 && idCard.charAt(idCard.length() - 1) == '0') {
				return true;
			} else {
				throw new Exception("Cédula Incorrecta.");
			}

		}
	}

	public static BigDecimal calcularCuotaMensual(Double valorPrestamo, Double interes, Double nCuotas) {
		if (interes == 0)
			return new BigDecimal(valorPrestamo / nCuotas);
		interes = interes / 1200; // Calcular el tipo mensual
		double numerador = interes * Math.pow(1 + interes, nCuotas);
		double denominador = Math.pow(1 + interes, nCuotas) - 1;
		Double cuota = valorPrestamo * (numerador / denominador);
		return new BigDecimal(cuota).setScale(2, RoundingMode.HALF_UP);
	}

	public static String randomAlphaNumeric() {
		int count = 10;
		String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	// Esta función servirá para validar la longitud de la cadena de texto
	private static boolean stringLength(String string, int length) {
		if (string.length() == length)
			return true;
		return false;
	}

	// Esta función sumará y multiplicará los primeros 9 dígitos de la cédula
	// Aquí se multiplica los dígitos impares por 2 y los pares por 1
	// Se usa el siguiente patrón 2.1.2.1.2.1.2.1.2
	// Se retorna la resta de la decena superior de la suma menos la suma
	// Es decir si sale la suma de todos los dígitos 36 entonces la decena superior
	// es 40
	// Por lo tanto debemos hacer lo siguiente (40-36)
	private static byte sumDigits(byte[] digits) {
		byte verifier;
		byte sum = 0;
		for (byte i = 0; i < digits.length; i = (byte) (i + 2)) {
			verifier = (byte) (digits[i] * 2);
			if (verifier > 9)
				verifier = (byte) (verifier - 9);
			sum = (byte) (sum + verifier);
		}
		for (byte i = 1; i < digits.length; i = (byte) (i + 2)) {
			verifier = (byte) (digits[i] * 1);
			sum = (byte) (sum + verifier);
		}
		return (byte) ((sum - (sum % 10) + 10) - sum);
	}
	/*
	 * Metodo que permite verificar si una cadena contiene numeros
	 * 
	 */

	public static boolean contieneCaracteresNoNumericos(String input) {
		return input.matches(".*[^0-9].*");
	}

	 public static String calcularEdadString(Date fechaNacimiento) {
	        if (fechaNacimiento == null) {
	            return "";
	        }

	        // Soportar tanto java.util.Date como java.sql.Date
	        LocalDate fechaNac = convertirALocalDate(fechaNacimiento);

	        LocalDate hoy = LocalDate.now(ZoneId.systemDefault());

	        if (fechaNac.isAfter(hoy)) {
	            return "";
	        }

	        Period periodo = Period.between(fechaNac, hoy);

	        int anios = periodo.getYears();
	        int meses = periodo.getMonths();
	        int dias  = periodo.getDays();

	        String textoAnios = formatearUnidad(anios, "año", "años");
	        String textoMeses = formatearUnidad(meses, "mes", "meses");
	        String textoDias  = formatearUnidad(dias, "día", "días");

	        return textoAnios + ", " + textoMeses + " y " + textoDias;
	    }

	    private static LocalDate convertirALocalDate(Date date) {
	        // Si es java.sql.Date usamos toLocalDate()
	        if (date instanceof java.sql.Date) {
	            return ((java.sql.Date) date).toLocalDate();
	        }
	        // Si es java.util.Date normal, usamos Instant
	        return java.time.Instant.ofEpochMilli(date.getTime())
	                .atZone(ZoneId.systemDefault())
	                .toLocalDate();
	    }

	    private static String formatearUnidad(int valor, String singular, String plural) {
	        if (valor == 1) {
	            return valor + " " + singular;
	        } else {
	            return valor + " " + plural;
	        }
	    }

}
