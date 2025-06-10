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
      <form onSubmit={handleLogin} style={{ display: "flex", flexDirection: "column", width: "300px" }}>
        <h2>Bem-vindo!</h2>
        <input
          type="text"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={{ marginBottom: "5px", padding: "8px" }}
          required
        />
        {errors.email && (
          <span style={{ color: "red", marginBottom: "10px", fontSize: "0.9em" }}>{errors.email}</span>
        )}
        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          style={{ marginBottom: "5px", padding: "8px" }}
          required
        />
        {errors.senha && (
          <span style={{ color: "red", marginBottom: "10px", fontSize: "0.9em" }}>{errors.senha}</span>
        )}

        {/* Esqueci minha senha */}
        <button
          type="button"
          onClick={handleRecuperarSenha}
          style={{
            background: "none",
            border: "none",
            color: "#007BFF",
            textDecoration: "underline",
            cursor: "pointer",
            marginBottom: "10px"
          }}
        >
          Esqueci minha senha
        </button>

        {/* Botão Entrar */}
        <button type="submit" style={{ padding: "10px", backgroundColor: "#4CAF50", color: "white", marginBottom: "10px" }}>
          Entrar
        </button>

        {/* Botão Cadastrar */}
        <button
          type="button"
          onClick={handleCadastro}
          style={{
            padding: "10px",
            backgroundColor: "#FFEB3B",
            color: "#000",
            fontWeight: "bold",
            border: "none",
            cursor: "pointer"
          }}
        >
          Cadastrar
        </button>
      </form>
    </div>
  );
}

export default LoginPage;