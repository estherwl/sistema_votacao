# Sistema de votação
## O que é?
API que gerencia um sistema de votação, com as seguintes funcionalidades:
* Usuário
  - Cadastrar um usuário, onde o cpf é verificado através de uma integração com outro serviço (para fins de teste, pode-se usar um gerador de cpfs válidos)
* Pauta
  - Cadastrar uma nova pauta, a qual desde a sua criação fica aberta para votação. Uma pauta fica aberta por padrão por 1 min, caso não seja enviado outro valor (em min) no campo encerramentoVotacao
  - Buscar pauta por id
  - Buscar pautas cadastradas
* Voto
  - Registrar voto, onde verifica-se se usuário/pauta estão cadastrados e se usuário já votou na pauta. Além disso, é analisado se a votação está encerrada (caso esteja, votos são contabilizados)
### Coleção do Postman para testes:
[![Run in Postman](https://run.pstmn.io/button.svg)](https://god.gw.postman.com/run-collection/18466784-d0ca06bd-e5dc-41b3-972b-0643213c856e?action=collection%2Ffork&collection-url=entityId%3D18466784-d0ca06bd-e5dc-41b3-972b-0643213c856e%26entityType%3Dcollection%26workspaceId%3D32e46bd6-f0df-4dc3-81dc-3b696a8dc8ab)
## Tecnologias utilizadas
* Kotlin
* Gradle
* Spring
* JPA
* PostgreSQL
