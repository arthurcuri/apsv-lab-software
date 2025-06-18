import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [errors, setErrors] = useState<{ email?: string; senha?: string }>({});
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({}); // Limpa erros anteriores
    try {
      const response = await api.post("/usuarios/login", {
        email,
        senha,
      });

      const { id, nome, tipo } = response.data;

      // Salva os dados do usuario no sessionStorage
      sessionStorage.setItem("usuarioId", id);
      sessionStorage.setItem("usuarioNome", nome);
      sessionStorage.setItem("usuarioTipo", tipo);

      alert("Login realizado com sucesso!");

      if (tipo === "ALUNO") {
        navigate("/dashboard-aluno");
      } else if (tipo === "PROFESSOR") {
        navigate("/dashboard-professor");
      } else if (tipo === "EMPRESA") {
        navigate("/cadastro-vantagem");
        } else if (tipo === "ADMIN") {
        navigate("/dashboard-admin");
      } else {
        alert("Tipo de usuário não reconhecido!");
      }
    } catch (error: any) {
      // Verifica se é erro de validação do backend
      if (error.response && error.response.status === 400 && error.response.data) {
        setErrors(error.response.data);
      } else {
        alert("Credenciais inválidas!");
      }
    }
  };

  const handleCadastro = () => {
    navigate("/cadastro");
  };

  const handleRecuperarSenha = () => {
    navigate("/recuperar-senha");
  };

  return (
    <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
      <form onSubmit={handleLogin} style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
        <h2>Bem-vindo!</h2>

        {/* Agrupamento dos campos com botão entrar na lateral */}
        <div style={{ display: "flex", alignItems: "center" }}>
          <div style={{ display: "flex", flexDirection: "column" }}>
            <input
              type="text"
              placeholder="Login"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              style={{ padding: "10px", width: "250px", marginBottom: "10px" }}
            />
            {errors.email && <span style={{ color: "red", fontSize: "0.9em" }}>{errors.email}</span>}

            <input
              type="password"
              placeholder="Senha"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              style={{ padding: "10px", width: "250px" }}
            />
            {errors.senha && <span style={{ color: "red", fontSize: "0.9em" }}>{errors.senha}</span>}
          </div>

          {/* Botão Entrar Circular */}
          <button
            type="submit"
            title="Entrar"
            style={{
              marginLeft: "15px",
              width: "50px",
              height: "50px",
              borderRadius: "50%",
              backgroundColor: "#4CAF50",
              color: "white",
              border: "none",
              cursor: "pointer",
              fontSize: "24px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            ➝
          </button>
        </div>

        {/* Opções abaixo */}
        <div style={{ display: "flex", justifyContent: "space-between", width: "250px", marginTop: "10px" }}>
          <button
            type="button"
            onClick={handleRecuperarSenha}
            style={{
              background: "none",
              border: "none",
              color: "#007BFF",
              textDecoration: "underline",
              cursor: "pointer",
            }}
          >
            Esqueci minha senha
          </button>

          <button
            type="button"
            onClick={handleCadastro}
            style={{
              padding: "5px 15px",
              backgroundColor: "#FFEB3B",
              color: "#000",
              fontWeight: "bold",
              border: "2px solid #cddc39",
              borderRadius: "8px",
              cursor: "pointer",
            }}
          >
            Cadastrar
          </button>
        </div>
      </form>
    </div>
  );
}

export default LoginPage;