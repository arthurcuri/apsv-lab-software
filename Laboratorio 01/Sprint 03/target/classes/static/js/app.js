// URLs das APIs
const API_BASE = "";
const API_ALUNOS = `${API_BASE}/aluno`;
const API_CURSOS = `${API_BASE}/curso`;
const API_DISCIPLINAS = `${API_BASE}/disciplina`;
const API_PROFESSORES = `${API_BASE}/professor`;
const API_PAGAMENTOS = `${API_BASE}/pagamento`;
const API_SECRETARIA = `${API_BASE}/secretaria`;

// Elementos da página
const contentTitle = document.getElementById("content-title");
const content = document.getElementById("content");

// Verificar e criar secretaria ao carregar a página
document.addEventListener("DOMContentLoaded", verificarOuCriarSecretaria);

// Funções para carregar as seções
function loadAlunos() {
  contentTitle.textContent = "Gerenciamento de Alunos";
  fetchAlunos();
}

function loadCursos() {
  contentTitle.textContent = "Gerenciamento de Cursos";
  fetchCursos();
}

function loadDisciplinas() {
  contentTitle.textContent = "Gerenciamento de Disciplinas";
  fetchDisciplinas();
}

function loadProfessores() {
  contentTitle.textContent = "Gerenciamento de Professores";
  fetchProfessores();
}

function loadSecretaria() {
  contentTitle.textContent = "Gerenciamento da Secretaria";
  fetchSecretaria();
}

// Funções para buscar dados da API
async function fetchAlunos() {
  try {
    const response = await fetch(API_ALUNOS);
    const alunos = await response.json();
    displayAlunos(alunos);
  } catch (error) {
    showError("Erro ao carregar alunos: " + error.message);
  }
}

async function fetchCursos() {
  try {
    const response = await fetch(API_CURSOS);
    const cursos = await response.json();
    displayCursos(cursos);
  } catch (error) {
    showError("Erro ao carregar cursos: " + error.message);
  }
}

async function fetchDisciplinas() {
  try {
    const response = await fetch(API_DISCIPLINAS);
    const disciplinas = await response.json();
    displayDisciplinas(disciplinas);
  } catch (error) {
    showError("Erro ao carregar disciplinas: " + error.message);
  }
}

async function fetchProfessores() {
  try {
    const response = await fetch(API_PROFESSORES);
    const professores = await response.json();
    displayProfessores(professores);
  } catch (error) {
    showError("Erro ao carregar professores: " + error.message);
  }
}

// Funções para exibir os dados
function displayAlunos(alunos) {
  let html = `
        <div class="row mb-3">
            <div class="col-md-12">
                <button class="btn btn-primary" onclick="showAlunoForm()">Adicionar Aluno</button>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Email</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
    `;

  if (alunos.length === 0) {
    html +=
      '<tr><td colspan="4" class="text-center">Nenhum aluno encontrado</td></tr>';
  } else {
    alunos.forEach((aluno) => {
      html += `
                <tr>
                    <td>${aluno.id}</td>
                    <td>${aluno.nome}</td>
                    <td>${aluno.email}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="showAlunoDisciplinas(${aluno.id})">Ver Disciplinas</button>
                        <button class="btn btn-sm btn-warning" onclick="showMatricularForm(${aluno.id})">Matricular</button>
                    </td>
                </tr>
            `;
    });
  }

  html += `
                    </tbody>
                </table>
            </div>
        </div>
        <div id="alunoForm" style="display: none;">
            <h3>Adicionar Aluno</h3>
            <form id="addAlunoForm">
                <div class="form-group">
                    <label for="nome">Nome</label>
                    <input type="text" class="form-control" id="nome" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" required>
                </div>
                <div class="form-group">
                    <label for="senha">Senha</label>
                    <input type="password" class="form-control" id="senha" required>
                </div>
                <button type="submit" class="btn btn-primary">Salvar</button>
                <button type="button" class="btn btn-secondary" onclick="hideAlunoForm()">Cancelar</button>
            </form>
        </div>
    `;

  content.innerHTML = html;

  // Adicionar evento de submit ao formulário
  const form = document.getElementById("addAlunoForm");
  if (form) {
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      addAluno();
    });
  }
}

function displayCursos(cursos) {
  let html = `
        <div class="row mb-3">
            <div class="col-md-12">
                <button class="btn btn-primary" onclick="showCursoForm()">Adicionar Curso</button>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
    `;

  if (cursos.length === 0) {
    html +=
      '<tr><td colspan="3" class="text-center">Nenhum curso encontrado</td></tr>';
  } else {
    cursos.forEach((curso) => {
      html += `
                <tr>
                    <td>${curso.id}</td>
                    <td>${curso.nome}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="showCursoDisciplinas(${curso.id})">Ver Disciplinas</button>
                        <button class="btn btn-sm btn-warning" onclick="showAddDisciplinaForm(${curso.id})">Adicionar Disciplina</button>
                    </td>
                </tr>
            `;
    });
  }

  html += `
                    </tbody>
                </table>
            </div>
        </div>
        <div id="cursoForm" style="display: none;">
            <h3>Adicionar Curso</h3>
            <form id="addCursoForm">
                <div class="form-group">
                    <label for="cursoNome">Nome</label>
                    <input type="text" class="form-control" id="cursoNome" required>
                </div>
                <button type="submit" class="btn btn-primary">Salvar</button>
                <button type="button" class="btn btn-secondary" onclick="hideCursoForm()">Cancelar</button>
            </form>
        </div>
    `;

  content.innerHTML = html;

  // Adicionar evento de submit ao formulário
  const form = document.getElementById("addCursoForm");
  if (form) {
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      addCurso();
    });
  }
}

function displayDisciplinas(disciplinas) {
  console.log("Exibindo todas as disciplinas:", disciplinas);

  let html = `
      <div class="row mb-3">
          <div class="col-md-12">
              <button class="btn btn-primary" onclick="showDisciplinaForm()">Adicionar Disciplina</button>
          </div>
      </div>
      <div class="row">
          <div class="col-md-12">
              <table class="table table-striped">
                  <thead>
                      <tr>
                          <th>ID</th>
                          <th>Nome</th>
                          <th>Créditos</th>
                          <th>Professor</th>
                          <th>Ações</th>
                      </tr>
                  </thead>
                  <tbody>
      `;

  if (!disciplinas || disciplinas.length === 0) {
    html +=
      '<tr><td colspan="5" class="text-center">Nenhuma disciplina encontrada</td></tr>';
  } else {
    disciplinas.forEach((disciplina) => {
      console.log("Processando disciplina na tabela principal:", disciplina);
      const professorNome = disciplina.professor
        ? disciplina.professor.nome
        : "Não atribuído";
      html += `
              <tr>
                  <td>${disciplina.id || "N/A"}</td>
                  <td>${disciplina.nome || "N/A"}</td>
                  <td>${disciplina.creditos || "0"}</td>
                  <td>${professorNome}</td>
                  <td>
                      <button class="btn btn-sm btn-primary" onclick="showAtribuirProfessorForm(${
                        disciplina.id
                      })">Atribuir Professor</button>
                  </td>
              </tr>
          `;
    });
  }

  html += `
                  </tbody>
              </table>
          </div>
      </div>
      <div id="disciplinaForm" style="display: none;">
          <h3>Adicionar Disciplina</h3>
          <form id="addDisciplinaForm">
              <div class="form-group">
                  <label for="disciplinaNome">Nome</label>
                  <input type="text" class="form-control" id="disciplinaNome" required>
              </div>
              <div class="form-group">
                  <label for="disciplinaCreditos">Créditos</label>
                  <input type="number" class="form-control" id="disciplinaCreditos" min="0" value="0" required>
              </div>
              <button type="submit" class="btn btn-primary">Salvar</button>
              <button type="button" class="btn btn-secondary" onclick="hideDisciplinaForm()">Cancelar</button>
          </form>
      </div>
  `;

  content.innerHTML = html;

  // Adicionar evento de submit ao formulário
  const form = document.getElementById("addDisciplinaForm");
  if (form) {
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      addDisciplina();
    });
  }
}

function displayProfessores(professores) {
  console.log(professores);
  let html = `
        <div class="row mb-3">
            <div class="col-md-12">
                <button class="btn btn-primary" onclick="showProfessorForm()">Adicionar Professor</button>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Email</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
    `;

  if (professores.length === 0) {
    html +=
      '<tr><td colspan="5" class="text-center">Nenhum professor encontrado</td></tr>';
  } else {
    professores.forEach((professor) => {
      html += `
                <tr>
                    <td>${professor.id}</td>
                    <td>${professor.nome}</td>
                    <td>${professor.email}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="showProfessorDisciplinas(${professor.id})">Ver Disciplinas</button>
                    </td>
                </tr>
            `;
    });
  }

  html += `
                    </tbody>
                </table>
            </div>
        </div>
        <div id="professorForm" style="display: none;">
            <h3>Adicionar Professor</h3>
            <form id="addProfessorForm">
                <div class="form-group">
                    <label for="professorNome">Nome</label>
                    <input type="text" class="form-control" id="professorNome" required>
                </div>
                <div class="form-group">
                    <label for="professorEmail">Email</label>
                    <input type="email" class="form-control" id="professorEmail" required>
                </div>
                <div class="form-group">
                    <label for="professorSenha">Senha</label>
                    <input type="password" class="form-control" id="professorSenha" required>
                </div>
                <button type="submit" class="btn btn-primary">Salvar</button>
                <button type="button" class="btn btn-secondary" onclick="hideProfessorForm()">Cancelar</button>
            </form>
        </div>
    `;

  content.innerHTML = html;

  // Adicionar evento de submit ao formulário
  const form = document.getElementById("addProfessorForm");
  if (form) {
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      addProfessor();
    });
  }
}

// Funções para mostrar/esconder formulários
function showAlunoForm() {
  document.getElementById("alunoForm").style.display = "block";
}

function hideAlunoForm() {
  document.getElementById("alunoForm").style.display = "none";
}

function showCursoForm() {
  document.getElementById("cursoForm").style.display = "block";
}

function hideCursoForm() {
  document.getElementById("cursoForm").style.display = "none";
}

function showDisciplinaForm() {
  document.getElementById("disciplinaForm").style.display = "block";
}

function hideDisciplinaForm() {
  document.getElementById("disciplinaForm").style.display = "none";
}

function showProfessorForm() {
  document.getElementById("professorForm").style.display = "block";
}

function hideProfessorForm() {
  document.getElementById("professorForm").style.display = "none";
}

// Funções para adicionar registros
async function addAluno() {
  const nome = document.getElementById("nome").value;
  const email = document.getElementById("email").value;
  const senha = document.getElementById("senha").value;

  try {
    const response = await fetch(API_ALUNOS, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ nome, email, senha }),
    });

    if (response.ok) {
      hideAlunoForm();
      fetchAlunos();
      showSuccess("Aluno adicionado com sucesso!");
    } else {
      showError("Erro ao adicionar aluno.");
    }
  } catch (error) {
    showError("Erro ao adicionar aluno: " + error.message);
  }
}

async function addCurso() {
  const nome = document.getElementById("cursoNome").value;

  try {
    const response = await fetch(API_CURSOS, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ nome }),
    });

    if (response.ok) {
      hideCursoForm();
      fetchCursos();
      showSuccess("Curso adicionado com sucesso!");
    } else {
      showError("Erro ao adicionar curso.");
    }
  } catch (error) {
    showError("Erro ao adicionar curso: " + error.message);
  }
}

async function addDisciplina() {
  const nome = document.getElementById("disciplinaNome").value;
  const creditos = document.getElementById("disciplinaCreditos").value;

  try {
    const response = await fetch(API_DISCIPLINAS, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ nome, creditos: parseInt(creditos) }),
    });

    if (response.ok) {
      hideDisciplinaForm();
      fetchDisciplinas();
      showSuccess("Disciplina adicionada com sucesso!");
    } else {
      showError("Erro ao adicionar disciplina.");
    }
  } catch (error) {
    showError("Erro ao adicionar disciplina: " + error.message);
  }
}

async function addProfessor() {
  const nome = document.getElementById("professorNome").value;
  const email = document.getElementById("professorEmail").value;
  const senha = document.getElementById("professorSenha").value;

  try {
    const response = await fetch(API_PROFESSORES, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ nome, email, senha }),
    });

    if (response.ok) {
      hideProfessorForm();
      fetchProfessores();
      showSuccess("Professor adicionado com sucesso!");
    } else {
      showError("Erro ao adicionar professor.");
    }
  } catch (error) {
    showError("Erro ao adicionar professor: " + error.message);
  }
}

// Funções para matricular aluno e atribuir professor
async function showMatricularForm(alunoId) {
  try {
    const response = await fetch(API_DISCIPLINAS);
    const disciplinas = await response.json();

    let html = `
            <div class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" id="matricularModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Matricular Aluno em Disciplina</h5>
                            <button type="button" class="btn-close" onclick="closeModal()"></button>
                        </div>
                        <div class="modal-body">
                            <form id="matricularForm">
                                <input type="hidden" id="matricularAlunoId" value="${alunoId}">
                                <div class="form-group">
                                    <label for="disciplinaId">Disciplina</label>
                                    <select class="form-control" id="disciplinaId" required>
                                        <option value="">Selecione uma disciplina</option>
        `;

    disciplinas.forEach((disciplina) => {
      html += `<option value="${disciplina.id}">${disciplina.nome}</option>`;
    });

    html += `
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                            <button type="button" class="btn btn-primary" onclick="matricularAluno()">Matricular</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

    // Adicionar o modal ao final do body
    const modalContainer = document.createElement("div");
    modalContainer.id = "modalContainer";
    modalContainer.innerHTML = html;
    document.body.appendChild(modalContainer);
  } catch (error) {
    showError("Erro ao carregar disciplinas: " + error.message);
  }
}

async function showAlunoDisciplinas(alunoId) {
  try {
    const response = await fetch(
      `${API_ALUNOS}/disciplinas?alunoId=${alunoId}`
    );
    const disciplinas = await response.json();

    let html = `
            <div class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" id="disciplinasModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Disciplinas do Aluno</h5>
                            <button type="button" class="btn-close" onclick="closeModal()"></button>
                        </div>
                        <div class="modal-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nome</th>
                                        <th>Professor</th>
                                        <th>Ações</th>
                                    </tr>
                                </thead>
                                <tbody>
        `;

    if (disciplinas.length === 0) {
      html +=
        '<tr><td colspan="4" class="text-center">Nenhuma disciplina encontrada</td></tr>';
    } else {
      disciplinas.forEach((disciplina) => {
        html += `
                    <tr>
                        <td>${disciplina.id}</td>
                        <td>${disciplina.nome}</td>
                        <td>${
                          disciplina.professor
                            ? disciplina.professor.nome
                            : "Não atribuído"
                        }</td>
                        <td>
                            <button class="btn btn-sm btn-danger" onclick="desmatricularAluno(${alunoId}, ${
          disciplina.id
        })">Desmatricular</button>
                        </td>
                    </tr>
                `;
      });
    }

    html += `
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick="closeModal()">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

    // Adicionar o modal ao final do body
    const modalContainer = document.createElement("div");
    modalContainer.id = "modalContainer";
    modalContainer.innerHTML = html;
    document.body.appendChild(modalContainer);
  } catch (error) {
    showError("Erro ao carregar disciplinas do aluno: " + error.message);
  }
}

async function showAddDisciplinaForm(cursoId) {
  try {
    const response = await fetch(API_DISCIPLINAS);
    const disciplinas = await response.json();

    let html = `
            <div class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" id="addDisciplinaModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Adicionar Disciplina ao Curso</h5>
                            <button type="button" class="btn-close" onclick="closeModal()"></button>
                        </div>
                        <div class="modal-body">
                            <form id="addDisciplinaToCursoForm">
                                <input type="hidden" id="addDisciplinaCursoId" value="${cursoId}">
                                <div class="form-group">
                                    <label for="addDisciplinaId">Disciplina</label>
                                    <select class="form-control" id="addDisciplinaId" required>
                                        <option value="">Selecione uma disciplina</option>
        `;

    disciplinas.forEach((disciplina) => {
      html += `<option value="${disciplina.id}">${disciplina.nome}</option>`;
    });

    html += `
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                            <button type="button" class="btn btn-primary" onclick="addDisciplinaToCurso()">Adicionar</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

    // Adicionar o modal ao final do body
    const modalContainer = document.createElement("div");
    modalContainer.id = "modalContainer";
    modalContainer.innerHTML = html;
    document.body.appendChild(modalContainer);
  } catch (error) {
    showError("Erro ao carregar disciplinas: " + error.message);
  }
}

async function showAtribuirProfessorForm(disciplinaId) {
  try {
    const response = await fetch(API_PROFESSORES);
    const professores = await response.json();

    let html = `
            <div class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" id="atribuirProfessorModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Atribuir Professor à Disciplina</h5>
                            <button type="button" class="btn-close" onclick="closeModal()"></button>
                        </div>
                        <div class="modal-body">
                            <form id="atribuirProfessorForm">
                                <input type="hidden" id="atribuirDisciplinaId" value="${disciplinaId}">
                                <div class="form-group">
                                    <label for="professorId">Professor</label>
                                    <select class="form-control" id="professorId" required>
                                        <option value="">Selecione um professor</option>
        `;

    professores.forEach((professor) => {
      html += `<option value="${professor.id}">${professor.nome}</option>`;
    });

    html += `
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                            <button type="button" class="btn btn-primary" onclick="atribuirProfessor()">Atribuir</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

    // Adicionar o modal ao final do body
    const modalContainer = document.createElement("div");
    modalContainer.id = "modalContainer";
    modalContainer.innerHTML = html;
    document.body.appendChild(modalContainer);
  } catch (error) {
    showError("Erro ao carregar professores: " + error.message);
  }
}

function closeModal() {
  const modalContainer = document.getElementById("modalContainer");
  if (modalContainer) {
    modalContainer.remove();
  }
}

async function matricularAluno() {
  const alunoId = document.getElementById("matricularAlunoId").value;
  const disciplinaId = document.getElementById("disciplinaId").value;

  if (!disciplinaId) {
    showError("Selecione uma disciplina.");
    return;
  }

  try {
    const response = await fetch(
      `${API_ALUNOS}/matricular?alunoId=${alunoId}&disciplinaId=${disciplinaId}`,
      {
        method: "POST",
      }
    );

    if (response.ok) {
      closeModal();
      showSuccess("Aluno matriculado com sucesso!");
    } else {
      showError("Erro ao matricular aluno.");
    }
  } catch (error) {
    showError("Erro ao matricular aluno: " + error.message);
  }
}

async function addDisciplinaToCurso() {
  const cursoId = document.getElementById("addDisciplinaCursoId").value;
  const disciplinaId = document.getElementById("addDisciplinaId").value;

  if (!disciplinaId) {
    showError("Selecione uma disciplina.");
    return;
  }

  try {
    const response = await fetch(
      `${API_CURSOS}/add-disciplina?cursoId=${cursoId}&disciplinaId=${disciplinaId}`,
      {
        method: "POST",
      }
    );

    if (response.ok) {
      closeModal();
      showSuccess("Disciplina adicionada ao curso com sucesso!");
    } else {
      showError("Erro ao adicionar disciplina ao curso.");
    }
  } catch (error) {
    showError("Erro ao adicionar disciplina ao curso: " + error.message);
  }
}

async function atribuirProfessor() {
  const disciplinaId = document.getElementById("atribuirDisciplinaId").value;
  const professorId = document.getElementById("professorId").value;

  if (!professorId) {
    showError("Selecione um professor.");
    return;
  }

  try {
    const response = await fetch(
      `${API_PROFESSORES}/adicionar-disciplina?professorId=${professorId}&disciplinaId=${disciplinaId}`,
      {
        method: "POST",
      }
    );

    if (response.ok) {
      closeModal();
      fetchDisciplinas();
      showSuccess("Professor atribuído com sucesso!");
    } else {
      showError("Erro ao atribuir professor.");
    }
  } catch (error) {
    showError("Erro ao atribuir professor: " + error.message);
  }
}

// Funções para exibir mensagens
function showError(message) {
  showMessage(message, "danger");
}

function showSuccess(message) {
  showMessage(message, "success");
}

function showMessage(message, type) {
  const alertDiv = document.createElement("div");
  alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
  alertDiv.role = "alert";
  alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;

  const container = document.querySelector(".container");
  container.insertBefore(alertDiv, container.firstChild);

  // Remover o alerta após 5 segundos
  setTimeout(() => {
    alertDiv.remove();
  }, 5000);
}

// Adicionar novas funções para mostrar disciplinas de cursos e professores
async function showCursoDisciplinas(cursoId) {
  try {
    const response = await fetch(
      `${API_CURSOS}/disciplinas?cursoId=${cursoId}`
    );
    const disciplinas = await response.json();
    let html = `
            <div class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" id="disciplinasModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Disciplinas do Curso</h5>
                            <button type="button" class="btn-close" onclick="closeModal()"></button>
                        </div>
                        <div class="modal-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nome</th>
                                        <th>Créditos</th>
                                        <th>Professor</th>
                                    </tr>
                                </thead>
                                <tbody>
        `;

    if (disciplinas.length === 0) {
      html +=
        '<tr><td colspan="4" class="text-center">Nenhuma disciplina encontrada</td></tr>';
    } else {
      disciplinas.forEach((disciplina) => {
        html += `
                    <tr>
                        <td>${disciplina.id}</td>
                        <td>${disciplina.nome}</td>
                        <td>${disciplina.creditos || "0"}</td>
                        <td>${
                          disciplina.professor
                            ? disciplina.professor.nome
                            : "Não atribuído"
                        }</td>
                    </tr>
                `;
      });
    }

    html += `
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick="closeModal()">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

    // Adicionar o modal ao final do body
    const modalContainer = document.createElement("div");
    modalContainer.id = "modalContainer";
    modalContainer.innerHTML = html;
    document.body.appendChild(modalContainer);
  } catch (error) {
    showError("Erro ao carregar disciplinas do curso: " + error.message);
  }
}

async function showProfessorDisciplinas(professorId) {
  try {
    console.log(`Buscando disciplinas do professor com ID ${professorId}`);
    const response = await fetch(
      `${API_PROFESSORES}/${professorId}/disciplinas`
    );
    const disciplinas = await response.json();

    console.log("Disciplinas recebidas:", disciplinas);

    let html = `
            <div class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" id="disciplinasModal" tabindex="-1">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Disciplinas do Professor</h5>
                            <button type="button" class="btn-close" onclick="closeModal()"></button>
                        </div>
                        <div class="modal-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nome</th>
                                        <th>Créditos</th>
                                        <th>Ações</th>
                                    </tr>
                                </thead>
                                <tbody>
        `;

    if (!disciplinas || disciplinas.length === 0) {
      html +=
        '<tr><td colspan="4" class="text-center">Nenhuma disciplina encontrada</td></tr>';
    } else {
      disciplinas.forEach((disciplina) => {
        console.log("Processando disciplina:", disciplina);
        html += `
                    <tr>
                        <td>${disciplina.id || "N/A"}</td>
                        <td>${disciplina.nome || "N/A"}</td>
                        <td>${disciplina.creditos || "0"}</td>
                        <td>
                            <button class="btn btn-sm btn-danger" onclick="removerDisciplinaProfessor(${professorId}, ${
          disciplina.id
        })">Remover</button>
                        </td>
                    </tr>
                `;
      });
    }

    html += `
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick="closeModal()">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

    // Adicionar o modal ao final do body
    const modalContainer = document.createElement("div");
    modalContainer.id = "modalContainer";
    modalContainer.innerHTML = html;
    document.body.appendChild(modalContainer);
  } catch (error) {
    console.error("Erro completo:", error);
    showError("Erro ao carregar disciplinas do professor: " + error.message);
  }
}

// Adicionar funções para desmatricular aluno e remover disciplina de professor
async function desmatricularAluno(alunoId, disciplinaId) {
  if (
    confirm("Tem certeza que deseja desmatricular o aluno desta disciplina?")
  ) {
    try {
      const response = await fetch(
        `${API_ALUNOS}/desmatricular?alunoId=${alunoId}&disciplinaId=${disciplinaId}`,
        {
          method: "POST",
        }
      );

      if (response.ok) {
        closeModal();
        showSuccess("Aluno desmatriculado com sucesso!");
        // Reabrir o modal com a lista atualizada
        showAlunoDisciplinas(alunoId);
      } else {
        showError("Erro ao desmatricular aluno.");
      }
    } catch (error) {
      showError("Erro ao desmatricular aluno: " + error.message);
    }
  }
}

async function removerDisciplinaProfessor(professorId, disciplinaId) {
  if (confirm("Tem certeza que deseja remover esta disciplina do professor?")) {
    try {
      const response = await fetch(
        `${API_PROFESSORES}/remover-disciplina?professorId=${professorId}&disciplinaId=${disciplinaId}`,
        {
          method: "POST",
        }
      );

      if (response.ok) {
        closeModal();
        showSuccess("Disciplina removida do professor com sucesso!");
        // Reabrir o modal com a lista atualizada
        showProfessorDisciplinas(professorId);
      } else {
        showError("Erro ao remover disciplina do professor.");
      }
    } catch (error) {
      showError("Erro ao remover disciplina do professor: " + error.message);
    }
  }
}

// Função para verificar e criar secretaria se necessário
async function verificarOuCriarSecretaria() {
  try {
    const response = await fetch(`${API_SECRETARIA}/check`);
    const data = await response.json();

    if (!data.exists) {
      criarSecretaria();
    }
  } catch (error) {
    console.error("Erro ao verificar secretaria:", error);
  }
}

// Função para criar uma secretaria
async function criarSecretaria() {
  try {
    const response = await fetch(API_SECRETARIA, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        nome: "Secretaria Principal",
        email: "secretaria@gmail.com",
        senha: "123456",
      }),
    });

    if (response.ok) {
      console.log("Secretaria criada com sucesso");
    } else {
      console.error("Erro ao criar secretaria");
    }
  } catch (error) {
    console.error("Erro ao criar secretaria:", error);
  }
}

// Função para buscar dados da secretaria
async function fetchSecretaria() {
  try {
    const response = await fetch(API_SECRETARIA);
    const secretarias = await response.json();
    displaySecretaria(secretarias);
  } catch (error) {
    showError("Erro ao carregar secretaria: " + error.message);
  }
}

// Função para exibir a secretaria
function displaySecretaria(secretarias) {
  const secretaria = secretarias.length > 0 ? secretarias[0] : null;

  let html = `
      <div class="row mb-3">
          <div class="col-md-12">
              <div class="card">
                  <div class="card-header bg-info text-white">
                      <h4>Status do Período de Matrículas</h4>
                  </div>
                  <div class="card-body">
                      <p>Status atual: <strong>${
                        secretaria && secretaria.matriculasAbertas
                          ? "ABERTO"
                          : "FECHADO"
                      }</strong></p>
                      <div class="d-flex gap-2">
                          <button class="btn btn-success" onclick="alterarStatusMatriculas(true)">Abrir Período</button>
                          <button class="btn btn-danger" onclick="alterarStatusMatriculas(false); enviarBoleto()">Fechar Período</button>
                      </div>
                  </div>
              </div>
          </div>
      </div>
  `;

  content.innerHTML = html;
}

// Função para alterar o status do período de matrículas
async function alterarStatusMatriculas(status) {
  try {
    const endpoint = status
      ? `${API_SECRETARIA}/abrir-matriculas`
      : `${API_SECRETARIA}/fechar-matriculas`;
    const response = await fetch(endpoint, {
      method: "POST",
    });

    if (response.ok) {
      showSuccess(
        `Período de matrículas ${status ? "aberto" : "fechado"} com sucesso!`
      );
      fetchSecretaria();
    } else {
      showError(
        `Erro ao ${status ? "abrir" : "fechar"} período de matrículas.`
      );
    }
  } catch (error) {
    showError(`Erro ao alterar status das matrículas: ${error.message}`);
  }
}

async function enviarBoleto() {
  try {
    const response = await fetch(`${API_PAGAMENTOS}/realizar`, {
      method: "POST",
    });
  } catch (error) {
    showError(`Erro ao enviar boleto: ${error.message}`);
  }
}
