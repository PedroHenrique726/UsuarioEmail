package model.dao;

import java.rmi.Naming;
import java.util.Scanner;

public class Usuario {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		try {
			int idLogin = 0;
			String email = "";
			boolean loginSucesso = false;

			// Conexão
			UsuarioDao usuario = (UsuarioDao) Naming.lookup("rmi://LOCALHOST:3333/Email");

			System.out.println("========= Bem Vindo ao Gambiarra e-mail =========");
			System.out.println(
					"++ Dica: Para facilitar a navegação você pode usar apenas a primeira letra da palavra. ++");
			
			while (loginSucesso == false) {
				System.out.println("\n\nDigite 'criar' para criar uma conta ou 'login' para efetuar login:");
				String cliente = sc.nextLine().toLowerCase();
				switch (cliente) {
				// Criar conta
				case "criar":
				case "c":
					System.out.println("Digite o seu nome: ");
					String nome = sc.nextLine();
					System.out.println("Digite seu e-mail: ");
					String email1 = sc.nextLine();
					System.out.println("Digite sua senha: ");
					String senha = sc.nextLine();
					String criar = usuario.criarUsuario(nome, senha, email1);
					System.out.println(criar);
					break;
				// Login
				case "login":
				case "l":
					System.out.println("Digite seu e-mail: ");
					email = sc.nextLine();
					System.out.println("Digite sua senha: ");
					senha = sc.nextLine();

					if (!email.contains("@gambmail.com")) {
						email += "@gambmail.com";
					}
					loginSucesso = usuario.login(email, senha);
					
					
					if (!loginSucesso) {
						System.out.println("Email ou senha digitado errado!");
					} else {
						
						idLogin = usuario.idLogin(email);
						System.out.println(idLogin);
						nome = usuario.findNameByEmail(email);
						System.out.println("Login efetuado! Bem Vindo " + nome + "\n");
					}
					break;
				}
			}

			// LOGIN EFETUADO
			String cliente = "";
			do {

				System.out.println(
						"Digite 'Contatos' para ver opções os contatos, 'Emails' para ver opções de e-mail ou 'Desconectar'");
				cliente = sc.nextLine().toLowerCase();
				if (cliente.equals("contatos") || cliente.equals("c") || cliente.equals("contato")) {
					//
					do {
						System.out.println(
								"Digite 'Consultar', 'Adicionar' ou 'Deletar' para fazer alterações nos contatos ou digite 'voltar'");
						cliente = sc.nextLine().toLowerCase();
						if (cliente.equals("consultar") || cliente.equals("c")) {
							// consulta de contatos
							do {
								String contatos = usuario.consultarContatosCliente(idLogin);
								System.out.println(contatos);
								if (contatos.equals("Não há contatos")) {
									cliente = "voltar";
								}
								if (!contatos.equals("Não há contatos")) {
									System.out.println(
											"Para enviar mensagem para um dos contatos digite o número correspondente ou digite voltar.");
									cliente = sc.nextLine();
									boolean isNumeric = cliente.matches("[+-]?\\d*(\\.\\d+)?");

									if (cliente.equals("voltar") || cliente.equals("v")) {
										cliente = "voltar";
									}
									// Enviar e-mail navegando pelos contatos
									else if (isNumeric) {
										int numeroContato = Integer.parseInt(cliente);
										String[] separarContatos;
										contatos = usuario.consultarContatos(idLogin);
										separarContatos = contatos.split(",");

										System.out.println("Digite o assunto do e-mail: ");
										String assunto = sc.nextLine();
										System.out.println("Digite o corpo do e-mail: ");
										String mensagem = sc.nextLine();

										String resposta = usuario.criarMensagens(idLogin,
												separarContatos[numeroContato - 1], assunto, mensagem);
										System.out.println(resposta);
										cliente = "voltar";
									} else {
										System.out.println("Nenhuma opção válida selecionada.");
									}
								}
								// Voltar ao menu anterior

							} while (!cliente.equals("voltar"));
							// Adicionar contatos
						} else if (cliente.equals("adicionar") || cliente.equals("a")) { // Adicionar contatos
							System.out.println("Digite o e-mail que deseja adicionar aos contatos: ");
							String emailContatos = sc.nextLine();
							System.out.println(usuario.adicionarContatos(idLogin, emailContatos));
							// Apagar contatos
						} else if (cliente.equals("deletar") || cliente.equals("d")) { // apagar contatos
							System.out.println("Digite o e-mail do contato que deseja apagar: ");
							String emailApagar = sc.nextLine();
							String emailApagado = usuario.apagarContatos(idLogin, emailApagar);
							System.out.println(emailApagado);
							// Retornar ao menu anterior
						} else if (cliente.equals("voltar") || cliente.equals("v")) {
							cliente = "voltar";
						} else {
							System.out.println("Nenhuma opção válida selecionada.");
						}

					} while (!cliente.equals("voltar"));// fim DO contatos

				} else if (cliente.equals("emails") || cliente.equals("email") || cliente.equals("e")) { // opções de
																											// e-mail
					do {
						System.out.println(
								"Digite 'Consultar' para consultar seus e-mails, 'Novo' para criar um novo e-mail ou 'Voltar' para retornar ao menu anterior.");
						cliente = sc.nextLine().toLowerCase();
						//Consultar e-mails
						if (cliente.equals("consultar") || cliente.equals("consulta") || cliente.equals("c")) {
							do {
								System.out.println(
										"Digite 'Recebidos' para ver e-mails recebidos, 'Enviados' para ver e-mails enviados ou 'Voltar' para retornar ao menu anterior");
								cliente = sc.nextLine().toLowerCase();
								//E-mails recebidos
								if (cliente.equals("recebidos") || cliente.equals("recebido") || cliente.equals("r")) {
									String resposta = usuario.consultarMinhasMensagens(idLogin);
									if (resposta.equals("")) {
										System.out.println("Você não recebeu nenhum e-mail.");
									}
									System.out.println(resposta);
								} //E-mail enviados
								else if (cliente.equals("enviados") || cliente.equals("enviado")
										|| cliente.equals("e")) {
									String resposta = usuario.consultarMinhasMensagensEnviadas(idLogin);
									if (resposta.equals("")) {
										System.out.println("Você não enviou nenhum e-mail.");
									} else {
										System.out.println(resposta);
									}
								} //Voltar ao menu anterior
								else if (cliente.equals("voltar") || cliente.equals("v")) {
									cliente = "voltar";
								} else {
									System.out.println("Nenhuma opção válida selecionada.");
								}
							} while (!cliente.equals("voltar"));
							// Criar um e-mail novo
						} else if (cliente.equals("novo") || cliente.equals("n")) {
							System.out.println(
									"Digite 'Contatos' para enviar a partir da sua lista de contatos, digite o e-mail do destinatário para qual deseja enviar o e-mail ou 'Voltar': ");
							String contatosOuDigitar = sc.nextLine().toLowerCase();
							if (contatosOuDigitar.equals("contatos") || contatosOuDigitar.equals("c")) {//
								String contatos = usuario.consultarContatosCliente(idLogin);
								System.out.println(contatos);
								System.out.println(
										"\n\nPara Enviar um e-mail digite o numero do contato ou 'voltar' para retornar ao inicio. ");
								String enviarMensagemContatos = sc.nextLine().toLowerCase();
								boolean isNumeric = enviarMensagemContatos.matches("[+-]?\\d*(\\.\\d+)?");
								// Voltar ao menu anterior
								if (enviarMensagemContatos.contains("voltar") || enviarMensagemContatos.equals("v")) {
									cliente = "voltar";
								}
								// Enviar e-mail navegando pelos contatos
								else if (isNumeric) {
									int numeroContato = Integer.parseInt(enviarMensagemContatos);
									String[] separarContatos;
									contatos = usuario.consultarContatos(idLogin);
									separarContatos = contatos.split(",");

									System.out.println("Digite o assunto do e-mail: ");
									String assunto = sc.nextLine();
									System.out.println("Digite o corpo do e-mail: ");
									String mensagem = sc.nextLine();

									String resposta = usuario.criarMensagens(idLogin, separarContatos[numeroContato - 1],
											assunto, mensagem);
									System.out.println(resposta);
								}
							} else if (contatosOuDigitar.equals("voltar") || contatosOuDigitar.equals("v")) {
								cliente = "voltar";
							}
							// Envio de e-mail a partir de um e-mail digitado pelo cliente
							else {
								System.out.println("Digite o assunto do e-mail: ");
								String assunto = sc.nextLine();
								System.out.println("Digite o corpo do e-mail: ");
								String mensagem = sc.nextLine();
								String resposta = usuario.criarMensagens(idLogin, contatosOuDigitar, assunto, mensagem);
								System.out.println(resposta);
							}

						} //Voltar ao menu anterior
						else if (cliente.equals("voltar") || cliente.equals("v")) {
							cliente = "voltar";
						} else {
							System.out.println("Nenhuma opção válida selecionada.");
						}
					} while (!cliente.equals("voltar"));

				}
				else if (cliente.equals("desconectar") || cliente.equals("d")) { // desconectar
					cliente = "desconectar";
				} else {
					System.out.println("Nenhuma opção válida foi digitada"); // opçõeo inválida
				}

			} while (!cliente.equals("desconectar"));
			System.out.println("Obrigado por usar nosso e-mail. \nAté breve.");
			sc.close();
		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}
	}
}
