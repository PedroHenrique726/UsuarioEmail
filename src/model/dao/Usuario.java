package model.dao;

import java.rmi.Naming;
import java.util.Scanner;

public class Usuario {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		try {
			String email = "";
			boolean loginSucesso = false;
			UsuarioDao usuario = (UsuarioDao) Naming.lookup("rmi://LOCALHOST:3333/Email");

			System.out.println("========= Bem Vindo =========");
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
			
			
			//LOGIN EFETUADO
			String cliente = "";
			do {
			System.out.println("\n\n\n\nDigite 'contatos' para ver opções de Contatos" + 
			", 'emails' para ver opções de E-mail ou 'sair' para Sair:");
			cliente = sc.nextLine().toLowerCase();
			switch(cliente) {
			
			case "contatos":
							System.out.println("'Adicionar' para Adicionar Contatos, 'Consultar' para Consultar Contatos ou 'Apagar' para Apagar Contatos");
							String clienteContatos = sc.nextLine();
							switch(clienteContatos) {
								case "adicionar":
										System.out.println("Digite o e-mail que deseja adicionar aos contatos: ");
										String emailContatos = sc.nextLine();
										usuario.adicionarContatos(email, emailContatos);
										
									break;
								case "consultar":
										String contatos = usuario.consultarContatosCliente(email);
										System.out.println(contatos);
										System.out.println("\n\nPara Enviar um e-mail digite o numero do contato ou 'voltar' para retornar ao inicio. ");
										String enviarMensagemContatos = sc.nextLine().toLowerCase();
										boolean isNumeric =  enviarMensagemContatos.matches("[+-]?\\d*(\\.\\d+)?");
										
										if (enviarMensagemContatos.contains("voltar") ) {
																				
										}else if ( isNumeric){
											int numeroContato = Integer.parseInt(enviarMensagemContatos);
											String[] separarContatos;
											contatos = usuario.consultarContatos(email);
											separarContatos = contatos.split(", ");
											
											System.out.println("Digite o assunto do e-mail: ");
											String assunto = sc.nextLine();
											System.out.println("Digite o corpo do e-mail:");
											String mensagem = sc.nextLine();

											usuario.criarMensagens(email, separarContatos[numeroContato-1], assunto, mensagem);		
											System.out.println("Enviado com sucesso");
										}
										else {
											System.out.println("Nenhuma opção selecionada");
										}
									
									break;
								case "apagar":
										System.out.println("Digite o e-mail do contato que deseja apagar:");
										String emailApagar = sc.nextLine();
										String emailApagado = usuario.apagarContatos(email, emailApagar);
										System.out.println(emailApagado);
									break;
									
							}
				break;
				
			case "emails":
				System.out.println("Digite 'criar' para Criar um novo e-mails, 'recebidos' para consultar e-mails recebidos ou 'enviados' para consultar e-mails enviados: ");
				cliente = sc.nextLine();
								switch(cliente) {
									case "criar":
										System.out.println("Digite o e-mail do destinatário: ");
										String emailDestinatario = sc.nextLine();
										System.out.println("Digite o assunto do e-mail: ");
										String assunto = sc.nextLine();
										System.out.println("Digite o corpo do e-mail: ");
										String mensagem = sc.nextLine();
										
										usuario.criarMensagens(email, emailDestinatario, assunto, mensagem);
										System.out.println("E-mail enviado com sucesso");
									break;
									case "recebidos":
										String resposta = usuario.consultarMinhasMensagens(email);
										if (resposta.equals("")) {
											System.out.println("Você não recebeu nenhum e-mail.");
										}
										System.out.println(resposta);
										break;
										
									case "enviados": 
										String resposta1 = usuario.consultarMinhasMensagensEnviadas(email);
										if (resposta1.equals("")) {
											System.out.println("Você não enviou nenhum e-mail.");
										}else {
										System.out.println(resposta1);
										}
										break;
								}
				break;			
			}
			}while(!cliente.equals("sair"));
			sc.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
