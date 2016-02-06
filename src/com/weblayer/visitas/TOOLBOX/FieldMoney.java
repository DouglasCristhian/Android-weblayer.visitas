package com.weblayer.visitas.TOOLBOX;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

public class FieldMoney extends EditText {
	/**
	 * Indicativo de atualiza��o do campo.
	 */
	private boolean ib_update;

	/**
	 * Construtor da classe.
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FieldMoney(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		//
		// Inicializa o componente de acordo com suas propriedades.
		//
		ComponenteInicializar();
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param context
	 * @param attrs
	 */
	public FieldMoney(Context context, AttributeSet attrs) {
		super(context, attrs);

		//
		// Inicializa o componente de acordo com suas propriedades.
		//
		ComponenteInicializar();
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param context
	 */
	public FieldMoney(Context context) {
		super(context);

		//
		// Inicializa o componente de acordo com suas propriedades.
		//
		ComponenteInicializar();
	}

	/**
	 * Inicializa o componente.
	 */
	private void ComponenteInicializar() {
		//
		// Adiciona o evento de teclas.
		//
		this.setKeyListener(io_key_listener);

		//
		// Preenche o campo com a mascara a ser utilizada.
		//
		this.setText("0,00");

		//
		// Seta a sele��o na primeira casa.
		//
		this.setSelection(1);

		//
		// Adiciona o listener de mudan�a no texto.
		//
		this.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				String ls_valor_original = s.toString();
				//
				// Trambique.
				//
				if (ib_update) {
					ib_update = false;
					return;
				}

				//quando n�o existir caractere sair
				if (ls_valor_original.length() ==0)
					return;
				
				//
				// Se o valor original for menor do que 16 posi��es.
				// Obs: (Permite apenas digitar 16 caracteres)
				//
				if (ls_valor_original.length() < 16) {
					//
					// Controlar� mascara de edi��o do campo (casas decimais)
					//
					StringBuffer ls_mascara = new StringBuffer();

					//
					// Adiciona o valor original (contido no campo) com o
					// pr�ximo valor.
					// Permitindo apenas caracteres num�ricos.
					//
					ls_mascara.append(ls_valor_original.replaceAll("[^0-9]*",
							""));

					//
					// Receber� um n�mero LONG do campo at� o momento.
					//
					Long ln_number = new Long(ls_mascara.toString());

					//
					// Faz o replace necess�rio para manipula��o das casas
					// decimais.
					//
					ls_mascara.replace(0, ls_mascara.length(),
							ln_number.toString());

					//
					// Se conteudo menor do que 3.
					//
					if (ls_mascara.length() < 3) {
						//
						// Se o tamanho for de 1 posi��o.
						//
						if (ls_mascara.length() == 1) {
							//
							// Insere os caracteres em ordem.
							//
							ls_mascara.insert(0, "0").insert(0, ",")
									.insert(0, "0");
						}

						//
						// Se o tamanho for de 2 posi��es.
						//
						else if (ls_mascara.length() == 2) {
							//
							// Insere os caracteres em ordem.
							//
							ls_mascara.insert(0, ",").insert(0, "0");
						}
					}

					//
					// Se tiver um tamanho maior que 3.
					//
					else {
						//
						// Insere a virgula.
						//
						ls_mascara.insert(ls_mascara.length() - 2, ",");
					}

					//
					// Se tiver o tamanho de 6 posi��es.
					//
					if (ls_mascara.length() > 6) {
						//
						// Insere o ponto.
						//
						ls_mascara.insert(ls_mascara.length() - 6, '.');

						//
						// Se o tamanho for maior do que 10.
						//
						if (ls_mascara.length() > 10) {
							//
							// Insere o ponto.
							//
							ls_mascara.insert(ls_mascara.length() - 10, '.');

							//
							// Se for maior do que 14.
							//
							if (ls_mascara.length() > 14) {
								//
								// insere o ponto.
								//
								ls_mascara.insert(ls_mascara.length() - 14, '.');
							}
						}
					}

					//
					// Define que o campo esta atualizando.
					//
					ib_update = true;

					//
					// Roda o novo valor.
					//
					FieldMoney.this.setText(ls_mascara);

					//
					// Faz a sele��o.
					//
					FieldMoney.this.setSelection(ls_mascara.length());
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}
		});
	}

	private final KeylistenerNumber io_key_listener = new KeylistenerNumber();

	private class KeylistenerNumber extends NumberKeyListener {

		public int getInputType() {
			return InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;

		}

		@Override
		protected char[] getAcceptedChars() {
			return new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
					'9' };

		}
	}
}