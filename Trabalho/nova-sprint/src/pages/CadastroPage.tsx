import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function CadastroPage() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCpf] = useState("");
  const [cnpj, setCnpj] = useState("");
  const [senha, setSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");
  const [tipo, setTipo] = useState<"ALUNO" | "PROFESSOR" | "EMPRESA">("ALUNO");

  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (senha !== confirmarSenha) {
      alert("As senhas não coincidem!");
      return;
    }

    if (tipo === "EMPRESA" && !cnpj) {
      alert("CNPJ é obrigatório para empresa!");
      return;
    }
    if ((tipo === "ALUNO" || tipo === "PROFESSOR") && !cpf) {
      alert("CPF é obrigatório!");
      return;
    }

    try {
      const payload: any = {
        nome,
        email,
        senha,
        tipo,
      };
      if (tipo === "EMPRESA") {
        payload.cnpj = cnpj;
        payload.cpf = "12279768607"; 
      }
      if (tipo === "ALUNO" || tipo === "PROFESSOR") {
        payload.cpf = cpf;
        payload.cnpj = "60647662000180";
      }

      console.log("Payload enviado:", payload);

      const response = await api.post("/usuarios", payload);
      console.log("Cadastro realizado com sucesso:", response.data);
      alert("Usuário cadastrado com sucesso!");
      navigate("/"); // Redirecionar para login
    } catch (error: any) {
      console.error("Erro no cadastro:", error);
      console.log("Resposta do backend:", error.response?.data);
      alert("Erro ao cadastrar usuário: " + (error.response?.data?.message || ""));
    }
  };

  const handleVoltar = () => {
    navigate("/");
  };

  return (
    <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
      <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", width: "300px" }}>
        {/* Botão Voltar */}
        <button 
          type="button" 
          onClick={handleVoltar} 
          style={{
            alignSelf: "flex-start",
            marginBottom: "20px",
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            fontSize: "24px",
            cursor: "pointer",
            padding: "8px 16px",
            borderRadius: "50%"
          }}
        >
          ←
        </button>

        <h2>Cadastrar</h2>
        <select
          value={tipo}
          onChange={e => setTipo(e.target.value as "ALUNO" | "PROFESSOR" | "EMPRESA")}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        >
          <option value="ALUNO">Aluno</option>
          <option value="PROFESSOR">Professor</option>
          <option value="EMPRESA">Empresa</option>
        </select>
        <input
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        {tipo === "EMPRESA" ? (
          <input
            type="text"
            placeholder="CNPJ"
            value={cnpj}
            onChange={(e) => setCnpj(e.target.value)}
            style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
            required
          />
        ) : (
          <input
            type="text"
            placeholder="CPF"
            value={cpf}
            onChange={(e) => setCpf(e.target.value)}
            style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
            required
          />
        )}
        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <input
          type="password"
          placeholder="Confirmar Senha"
          value={confirmarSenha}
          onChange={(e) => setConfirmarSenha(e.target.value)}
          style={{ marginBottom: "10px", padding: "8px", borderRadius: "8px" }}
          required
        />
        <button
          type="submit"
          style={{
            padding: "10px",
            backgroundColor: "#4CAF50",
            color: "white",
            borderRadius: "8px"
          }}
        >
          Salvar dados
        </button>
      </form>
    </div>
  );
}

export default CadastroPage;