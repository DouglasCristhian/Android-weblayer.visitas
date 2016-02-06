package com.weblayer.visitas.TOOLBOX;


import android.annotation.SuppressLint;
import java.text.DecimalFormat;

public class CPFCNPJUtil {

	private static final String VALUE_CANNOT_BE_NULL = "value cannot be null";

	private static final String VALUE_CANNOT_BE_NULL_OR_EMPTY = "value cannot be null or empty";

	private static final String SIZE_OF_VALUE_CANNOT_BE_BIGGER_THEN_14 = "size of value cannot be bigger then 14";

	private static final String VALUE_IS_NOT_A_VALID_CPF_OR_CPNJ = "value is not a valid CPF or CPNJ";

	private static final DecimalFormat CNPJ_NFORMAT = new DecimalFormat(
			"00000000000000");

	private static final DecimalFormat CPF_NFORMAT = new DecimalFormat(
			"00000000000");

	private static final int[] weightCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static final int[] weightCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4,
			3, 2 };



	@SuppressLint("NewApi")
	public static String formatCPForCPNJ(String value) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException(VALUE_CANNOT_BE_NULL_OR_EMPTY);
		}
		return formatCPForCPNJ(Long.parseLong(value.replaceAll("[^0-9]+", "")),
				true);
	}

	/**
	 * Formata valor para CPF 000.000.000-00 ou CNPJ 00.000.000/0000-00
	 * 
	 * @param value
	 *            [string] representa um CPF ou CNPJ
	 * 
	 * @param check
	 *            [boolean] se true verifica se � um CPF ou CNPJ valido, se
	 *            false apenas realiza a formata��o
	 * 
	 * @return CPF ou CNPJ formatado
	 */
	
	@SuppressLint("NewApi")
	public static String formatCPForCPNJ(String value, boolean check) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException(VALUE_CANNOT_BE_NULL_OR_EMPTY);
		}
		return formatCPForCPNJ(Long.parseLong(value.replaceAll("[^0-9]+", "")),
				check);
	}

	/**
	 * Formata valor para CPF 000.000.000-00 ou CNPJ 00.000.000/0000-00 caso o
	 * mesmo seja um CPF ou CNPJ v�lido
	 * 
	 * @param value
	 *            [long] representa um CPF ou CNPJ
	 * 
	 * @return CPF ou CNPJ formatado
	 */
	public static String formatCPForCPNJ(Long value) {
		return formatCPForCPNJ(value, true);
	}

	/**
	 * Formata valor para CPF 000.000.000-00 ou CNPJ 00.000.000/0000-00
	 * 
	 * @param value
	 *            [long] representa um CPF ou CNPJ
	 * 
	 * @param check
	 *            [boolean] se true verifica se � um CPF ou CNPJ valido, se
	 *            false apenas realiza a formata��o
	 * 
	 * @return CPF ou CNPJ formatado
	 */
	public static String formatCPForCPNJ(Long value, boolean check) {
		if (value == null) {
			throw new IllegalArgumentException(VALUE_CANNOT_BE_NULL);
		}

		final int valueSize = value.toString().length();
		if (valueSize > 14) {
			throw new IllegalArgumentException(
					SIZE_OF_VALUE_CANNOT_BE_BIGGER_THEN_14);
		}

		if (check && !isCPForCPNJ(value)) {
			throw new IllegalArgumentException(VALUE_IS_NOT_A_VALID_CPF_OR_CPNJ);
		}

		boolean isCPF = valueSize < 12;
		DecimalFormat formatDecimal = isCPF ? CPF_NFORMAT : CNPJ_NFORMAT;

		final String stringNumber = formatDecimal.format(value);

		return isCPF ? stringNumber.replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4") : stringNumber.replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})","$1.$2.$3/$4-$5");
	}

	/**
	 * Verifica se um valor corresponde � um CPF ou CNPJ v�lido.
	 * 
	 * @param value
	 *            [string] valor � ser testado
	 * 
	 * @return [boolean] true caso seja um valor v�lido, false caso contr�rio
	 */
	@SuppressLint("NewApi")
	public static boolean isCPForCPNJ(String value) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException(VALUE_CANNOT_BE_NULL_OR_EMPTY);
		}
		return isCPForCPNJ(Long.parseLong(value.replaceAll("[^0-9]+", "")));
	}

	/**
	 * Verifica se um valor corresponde � um CPF ou CNPJ v�lido.
	 * 
	 * @param value
	 *            [long] valor � ser testado
	 * 
	 * @return [boolean] true caso seja um valor v�lido, false caso contr�rio
	 */
	public static boolean isCPForCPNJ(Long value) {

		final int valueSize = value.toString().length();
		if (valueSize > 14) {
			return false;
		}

		boolean isCPF = valueSize < 12;

		return isCPF ? isCPF(value) : isCNPJ(value);
	}

	/**
	 * Verifica se um valor corresponde � um CPF.
	 * 
	 * @param value
	 *            [long] valor � ser testado
	 * 
	 * @return [boolean] true caso seja um valor v�lido, false caso contr�rio
	 */
	private static boolean isCPF(Long value) {

		String CPF = CPF_NFORMAT.format(value);

		int firstPart = calcDigit(CPF.substring(0, 9), weightCPF);
		int lastPart = calcDigit(CPF.substring(0, 9) + firstPart, weightCPF);

		return CPF.substring(9).equals(
				String.format("%d%d", firstPart, lastPart));
	}

	/**
	 * Verifica se um valor corresponde � um CNPJ.
	 * 
	 * @param value
	 *            [long] valor � ser testado
	 * 
	 * @return [boolean] true caso seja um valor v�lido, false caso contr�rio
	 */
	private static boolean isCNPJ(Long value) {

		String CNPJ = CNPJ_NFORMAT.format(value);

		Integer firstPart = calcDigit(CNPJ.substring(0, 12), weightCNPJ);
		Integer lastPart = calcDigit(CNPJ.substring(0, 12) + firstPart,
				weightCNPJ);

		return CNPJ.substring(12).equals(
				String.format("%d%d", firstPart, lastPart));
	}

	/**
	 * Calcula digito verificador para CPF or CPNJ
	 * 
	 * @param stringBase
	 *            [string] base do calculo do digito verificador
	 * 
	 * @param weight
	 *            array[int] representa os peso de cada caracter que comp�e um
	 *            CPF ou CNPJ
	 * 
	 * @return [int] digito verificador
	 */
	private static int calcDigit(String stringBase, int[] weight) {
		int sum = 0;
		for (int index = stringBase.length() - 1, digit; index >= 0; index--) {
			digit = Integer.parseInt(stringBase.substring(index, index + 1));
			sum += digit * weight[weight.length - stringBase.length() + index];
		}
		sum = 11 - sum % 11;
		return sum > 9 ? 0 : sum;
	}
}
