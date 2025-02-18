# 🎓 Histórias de Usuário

## 🎥 História de Usuário 1: Matrícula em Disciplinas

**Eu como aluno**  
Quero me matricular em disciplinas para o próximo semestre  
Para que eu possa participar das aulas e cumprir os requisitos do curso.

### 📄 Contexto/ Dores:
Os alunos precisam garantir sua participação nas disciplinas obrigatórias e optativas para continuar seus estudos sem atrasos.

### 🎯 Objetivo da US:
Permitir que o aluno realize sua matrícula de forma eficiente e dentro dos critérios definidos pela instituição.

### 📚 Regras de negócio:
- O aluno pode escolher até 4 disciplinas obrigatórias e 2 optativas.
- O sistema deve verificar se há vagas disponíveis antes de confirmar a matrícula.
- O aluno só pode se matricular dentro do período de matrículas.

### 📝 Critérios de Aceite:
**Critério 1: Limite de disciplinas**  
Dado que o aluno está matriculado  
Quando ele tenta adicionar mais de 4 disciplinas obrigatórias ou mais de 2 optativas  
Então o sistema deve exibir uma mensagem informando o limite de disciplinas.

**Critério 2: Verificação de vagas**  
Dado que o aluno seleciona uma disciplina  
Quando não houver vagas disponíveis  
Então o sistema deve exibir uma mensagem informando a falta de vagas.

**Critério 3: Período de matrícula**  
Dado que o aluno tenta se matricular  
Quando estiver fora do período de matrículas  
Então o sistema deve bloquear a ação e exibir uma mensagem informativa.

---

## 🎥 História de Usuário 2: Cancelar Matrícula

**Eu como aluno**  
Quero cancelar uma disciplina na qual já estou matriculado  
Para ajustar minha carga horária ou mudar de disciplina.

### 📄 Contexto/ Dores:
Os alunos podem precisar cancelar disciplinas para organizar melhor sua rotina acadêmica.

### 🎯 Objetivo da US:
Permitir que os alunos cancelem disciplinas dentro do período de matrícula.

### 📚 Regras de negócio:
- O aluno pode cancelar disciplinas apenas dentro do período de matrículas.
- O sistema deve atualizar automaticamente a quantidade de vagas disponíveis na disciplina.
- O sistema deve notificar o aluno sobre o cancelamento bem-sucedido.

### 📝 Critérios de Aceite:
**Critério 1: Cancelamento dentro do prazo**  
Dado que o aluno deseja cancelar uma disciplina  
Quando ele estiver dentro do período de matrícula  
Então o sistema deve permitir o cancelamento.

**Critério 2: Atualização de vagas**  
Dado que o aluno cancela a disciplina  
Quando o cancelamento for concluído  
Então o sistema deve aumentar a quantidade de vagas disponíveis.

**Critério 3: Notificação**  
Dado que o cancelamento foi realizado  
Quando ele for processado pelo sistema  
Então o aluno deve receber uma notificação confirmando o cancelamento.

---

## 🎥 História de Usuário 3: Consultar Disciplinas Matriculadas

**Eu como aluno**  
Quero visualizar as disciplinas nas quais estou matriculado  
Para acompanhar minha grade curricular.

### 📄 Contexto/ Dores:
Os alunos precisam acompanhar sua carga horária e horários para organizar melhor seus estudos.

### 🎯 Objetivo da US:
Facilitar o acesso às informações sobre as disciplinas matriculadas.

### 📚 Regras de negócio:
- O aluno pode visualizar todas as disciplinas nas quais está matriculado.
- As informações devem incluir nome da disciplina, professor e horários.

### 📝 Critérios de Aceite:
**Critério 1: Visualização da grade**  
Dado que o aluno acessa sua área de matrícula  
Quando ele solicita a visualização das disciplinas  
Então o sistema deve exibir a lista com nome da disciplina, professor e horários.

---

## 🎥 História de Usuário 4: Gerenciar Disciplinas

**Eu como secretaria**  
Quero cadastrar, editar e remover disciplinas  
Para organizar a grade curricular de cada curso.

### 📄 Contexto/ Dores:
A instituição precisa manter um controle eficiente sobre as disciplinas ofertadas.

### 🎯 Objetivo da US:
Permitir que a secretaria administre as disciplinas conforme necessário.

### 📚 Regras de negócio:
- A secretaria pode criar novas disciplinas com nome, carga horária e professor responsável.
- A secretaria pode editar informações das disciplinas antes do período de matrículas.
- A secretaria pode remover disciplinas que não tenham alunos matriculados.

### 📝 Critérios de Aceite:
**Critério 1: Cadastro de disciplinas**  
Dado que a secretaria deseja adicionar uma disciplina  
Quando ela preenche os dados necessários  
Então o sistema deve permitir o cadastro.

**Critério 2: Edição de disciplinas**  
Dado que a secretaria deseja editar uma disciplina  
Quando ela ainda não tem alunos matriculados  
Então o sistema deve permitir a edição.

**Critério 3: Remoção de disciplinas**  
Dado que a secretaria deseja excluir uma disciplina  
Quando não houver alunos matriculados  
Então o sistema deve permitir a remoção.

---

## 🎥 História de Usuário 5: Gerenciar Professores

**Eu como secretaria**  
Quero associar professores às disciplinas  
Para que cada professor saiba quais turmas irá lecionar.

### 📄 Contexto/ Dores:
Os professores precisam ser atribuídos corretamente às disciplinas para garantir a organização do semestre.

### 🎯 Objetivo da US:
Facilitar o gerenciamento de professores e suas disciplinas.

### 📚 Regras de negócio:
- A secretaria pode cadastrar e editar informações dos professores.
- Um professor pode ser vinculado a várias disciplinas.
- O professor deve ser notificado quando for designado a uma nova disciplina.

### 📝 Critérios de Aceite:
**Critério 1: Cadastro de professor**  
Dado que a secretaria deseja cadastrar um professor  
Quando ela preenche os dados obrigatórios  
Então o sistema deve permitir o cadastro.

**Critério 2: Notificação ao professor**  
Dado que um professor foi vinculado a uma disciplina  
Quando essa ação for concluída  
Então ele deve receber uma notificação.

---

## 🎥 História de Usuário 6: Consultar Alunos Matriculados

**Eu como professor**  
Quero visualizar a lista de alunos matriculados nas disciplinas  
Para me organizar e acompanhar a turma.

### 📄 Contexto/ Dores:
Os professores precisam saber quais alunos estão matriculados para planejar suas aulas.

### 🎯 Objetivo da US:
Facilitar o acesso dos professores à lista de alunos matriculados.

### 📚 Regras de negócio:
- O professor pode acessar a lista de alunos nas disciplinas.
- As informações devem incluir nome do aluno e matrícula.

### 📝 Critérios de Aceite:
**Critério 1: Visualização da lista de alunos**  
Dado que o professor acessa uma disciplina  
Quando ele solicita a lista de alunos  
Então o sistema deve exibir nome e matrícula dos alunos matriculados.


---

## 🎥 História 7: Notificar o Sistema de Cobrança

**Eu como** sistema de matrículas  
**Quero** notificar automaticamente o sistema de cobranças quando um aluno se matricular  
**Para** que a universidade possa gerar as mensalidades corretamente  

### 📄 Contexto/ Dores:
A universidade precisa garantir que os pagamentos sejam processados corretamente após a matrícula.

### 🎯 Objetivo da US:
Automatizar a notificação do sistema de cobranças.

### 📚 Regras de negócio:
- Sempre que um aluno finalizar sua matrícula, os dados devem ser enviados ao sistema de cobranças.
- O sistema de cobranças deve receber a lista de disciplinas e o valor total da matrícula.
- O aluno deve ser notificado sobre a cobrança pendente.

### 📝 Critérios de Aceite:
**Critério 1: Notificação automática**  
Dado que um aluno concluiu sua matrícula  
Quando as disciplinas forem confirmadas  
Então o sistema deve enviar os dados ao sistema de cobranças  

