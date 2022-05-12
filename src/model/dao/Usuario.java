package model.dao;

import java.rmi.Naming;
import java.util.Scanner;

public class Usuario {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		try {
		String email = "alisson@email.com";
			boolean loginSucesso = false;
			UsuarioDao usuario = (UsuarioDao) Naming.lookup("rmi://LOCALHOST:3333/Email");

			/*System.out.println("========= Bem Vindo =========");
			while (loginSucesso == false) {
				System.out.println("Digite 'criar' para criar uma conta ou 'login' para efetuar login:");
				String cliente = sc.nextLine().toLowerCase();
				switch (cliente) {

				case "criar":
					System.out.println("Digite o seu nome: ");
					String nome = sc.nextLine();
					System.out.println("Digite seu e-mail: ");
					String email1 = sc.nextLine();
					System.out.println("Digite sua senha: ");
					String senha = sc.nextLine();
					String criar = usuario.criarUsuario(nome, senha, email1);
					System.out.println(criar);
					break;
				case "login":
					System.out.println("Digite seu e-mail: ");
					email = sc.nextLine();
					System.out.println("Digite sua senha: ");
					senha = sc.nextLine();
					loginSucesso = usuario.login(email, senha);

					if (!loginSucesso) {
						System.out.println("Email ou senha digitado errado!");
					} else {
						nome = usuario.findNameByEmail(email);
						System.out.println("Login efetuado! Bem Vindo " + nome +"\n");

					}
					break;
				}
			}
			*/
			
			//LOGIN EFETUADO
			String cliente = "";
			do {
				
				System.out.println("Digite 'Contatos' para ver opções os contatos, 'Emails' para ver opções de e-mail ou 'Desconectar'");
				cliente = sc.nextLine().toLowerCase();
				if(cliente.equals("contatos") || cliente.equals("contato")) {// opções de contatos
					do {
						System.out.println("Digite 'Consultar', 'Adicionar' ou 'Apagar' para fazer alterações nos contatos ou digite 'voltar'");
						cliente = sc.nextLine().toLowerCase();
						if(cliente.equals("consultar")) { // consultar contatos
							//consulta de contatos
							do{
								String contatos = usuario.consultarContatosCliente(email);
								System.out.println(contatos);
								if (contatos.equals("Não há Contatos")) {
									cliente = "voltar";
								}
								if(!contatos.equals("Não há Contatos")) {
								System.out.println("Para enviar mensagem para um dos contatos digite o número correspondente ou digite voltar.");
								cliente = sc.nextLine();
								boolean isNumeric = cliente.matches("[+-]?\\d*(\\.\\d+)?");
								
								if (cliente.equals("voltar")) {
									
								}else if(isNumeric){
									int numeroContato = Integer.parseInt(cliente);
									String[] separarContatos;
									contatos = usuario.consultarContatos(email);
									separarContatos = contatos.split(", ");

									System.out.println("Digite o assunto do e-mail: ");
									String assunto = sc.nextLine();
									System.out.println("Digite o corpo do e-mail:");
									String mensagem = sc.nextLine();

									String resposta = usuario.criarMensagens(email, separarContatos[numeroContato - 1], assunto, mensagem);
									System.out.println(resposta);
									cliente = "voltar";
								}else {
									System.out.println("Nenhuma opção válida selecionada.");
								}
								}
							}while(!cliente.equals("voltar"));
							
							
						}else if(cliente.equals("adicionar")) { // Adicionar contatos
							System.out.println("Digite o e-mail que deseja adicionar aos contatos: ");
							String emailContatos = sc.nextLine();
							usuario.adicionarContatos(email, emailContatos);
						}else if(cliente.equals("apagar")) { // apagar contatos
							System.out.println("Digite o e-mail do contato que deseja apagar:");
							String emailApagar = sc.nextLine();
							String emailApagado = usuario.apagarContatos(email, emailApagar);
							System.out.println(emailApagado);
							
						}else if(cliente.equals("voltar")) { // voltar 
						
						}else {
							System.out.println("Nenhuma opção válida selecionada. ()");
						}					
						
					} while(!cliente.equals("voltar"));// fim DO contatos
				
				}else if(cliente.equals("emails") || cliente.equals("email")) { // opções de e-mail
					do {
						System.out.println("Digite 'Consultar' para consultar seus e-mails, 'Criar' para criar um novo e-mail ou 'Voltar' para retornar ao menu anterior.");
						cliente = sc.nextLine().toLowerCase();
						if(cliente.equals("consultar") || cliente.equals("consulta")) {
							do {
								System.out.println("Digite 'Recebidos' para ver e-mails recebidos, 'Enviados' para ver e-mails enviados ou 'Voltar' para retornar ao menu anterior");
								cliente = sc.nextLine().toLowerCase();
								if(cliente.equals("recebidos") || cliente.equals("recebido")) {
									String resposta = usuario.consultarMinhasMensagens(email);
									if (resposta.equals("")) {
										System.out.println("Você não recebeu nenhum e-mail.");
									}
									System.out.println(resposta);
									
								
								}else if(cliente.equals("enviados") || cliente.equals("enviado")) {
									String resposta = usuario.consultarMinhasMensagensEnviadas(email);
									if (resposta.equals("")) {
										System.out.println("Você não enviou nenhum e-mail.");
									} else {
										System.out.println(resposta);
									}
									
								
								}else if(cliente.equals("voltar")) {
									
								}else {
									System.out.println("Nenhuma opção válida selecionada.");
								}															
							}while(!cliente.equals("voltar"));
						}else if(cliente.equals("criar")) {
							System.out.println(
									"Digite 'Contatos' para enviar a partir da sua lista de contatos, digite o e-mail do destinatário para qual deseja enviar o e-mail ou 'Voltar': ");
							String contatosOuDigitar = sc.nextLine().toLowerCase();

							if (contatosOuDigitar.equals("contatos")) {// erro aqui
								String contatos = usuario.consultarContatosCliente(email);
								System.out.println(contatos);
								System.out.println(
										"\n\nPara Enviar um e-mail digite o numero do contato ou 'voltar' para retornar ao inicio. ");
								String enviarMensagemContatos = sc.nextLine().toLowerCase();
								boolean isNumeric = enviarMensagemContatos.matches("[+-]?\\d*(\\.\\d+)?");

								if (enviarMensagemContatos.contains("voltar")) {

								} else if (isNumeric) {
									int numeroContato = Integer.parseInt(enviarMensagemContatos);
									String[] separarContatos;
									contatos = usuario.consultarContatos(email);
									separarContatos = contatos.split(",");

									System.out.println("Digite o assunto do e-mail: ");
									String assunto = sc.nextLine();
									System.out.println("Digite o corpo do e-mail:");
									String mensagem = sc.nextLine();

									String resposta = usuario.criarMensagens(email, separarContatos[numeroContato - 1], assunto,
											mensagem);
									System.out.println(resposta);
								}
							}else {
								System.out.println("Digite o assunto do e-mail: ");
								String assunto = sc.nextLine();
								System.out.println("Digite o corpo do e-mail: ");
								String mensagem = sc.nextLine();
								String resposta = usuario.criarMensagens(email, contatosOuDigitar, assunto, mensagem);
								System.out.println(resposta);
							}
					
						
							
						}else if(cliente.equals("voltar")) {
							
						}
						
					}while(!cliente.equals("voltar"));
				}else if(cliente.equals("desconectar")) { // desconectar
					
				}else {
					System.out.println("Nenhuma opção válida foi digitada"); // opção inválida
				}
	
			}while(!cliente.equals("desconectar"));
			System.out.println("Obrigado por usar nosso e-mail. \nAté breve.");
			sc.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
