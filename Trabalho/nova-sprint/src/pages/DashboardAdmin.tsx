import { useEffect, useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

interface Usuario {
  id: number;
  nome: string;
}

interface Historico {
  id: number;
  destinoId: number;
  quantidade: number;
  motivo: string;
  data: string;
}

function DashboardAdmin() {
  const [saldo, setSaldo] = useState(0);
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  const [usuarioSelecionado, setUsuarioSelecionado] = useState("");
  const [quantidade, setQuantidade] = useState(0);
  const [motivo, setMotivo] = useState("");
  const [historico, setHistorico] = useState<Historico[]>([]);
  const [errors, setErrors] = useState<{ quantidade?: string; motivo?: string; alunoId?: string; erro?: string }>({});
  const navigate = useNavigate();

  const usuarioId = sessionStorage.getItem("usuarioId");

  useEffect(() => {
    if (usuarioId) {
      buscarSaldo();
      buscarUsuarios();
      buscarHistorico();
    } else {
      alert("Usuário não logado!");
      navigate("/"); // volta para login se não tiver ID
    }
  }, []);

  const buscarSaldo = async () => {
    try {
      const response = await api.get(`/usuarios/${usuarioId}/saldo`);
      setSaldo(response.data.saldo);
    } catch (error) {
      console.error("Erro ao buscar saldo:", error);
    }
  };

  // Alterado para buscar todos os usuários
  const buscarUsuarios = async () => {
    try {
      const response = await api.get("/usuarios");
      setUsuarios(response.data);
    } catch (error) {
      console.error("Erro ao buscar usuários:", error);
    }
  };

  const buscarHistorico = async () => {
    try {
      const response = await api.get(`/usuarios/${usuarioId}/historico`);
      setHistorico(response.data);
    } catch (error) {
      console.error("Erro ao buscar histórico:", error);
    }
  };

  const handleDistribuirMoedas = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({}); // Limpa erros anteriores

    try {
      await api.post(`/usuarios/${usuarioId}/distribuir-moedas`, {
        alunoId: usuarioSelecionado, // Mantém o nome do campo para compatibilidade backend
        quantidade,
        motivo
      });
      alert("Moedas distribuídas com sucesso!");
      buscarSaldo();
      buscarHistorico();
      setUsuarioSelecionado("");
      setQuantidade(0);
      setMotivo("");
    } catch (error: any) {
      // Se o back-end retornar um objeto de erros de validação
      if (error.response && error.response.status === 400 && error.response.data) {
        setErrors(error.response.data);
      } else {
        alert("Erro ao distribuir moedas.");
      }
    }
  };

  return (
    <div>
      <h1>Dashboard do Admin</h1>

      {/* Saldo */}
      <div style={{ marginBottom: "20px", fontSize: "18px" }}>
        <strong>Saldo de Moedas:</strong> {saldo}
      </div>

      {/* Formulário */}
      <form onSubmit={handleDistribuirMoedas} style={{ marginBottom: "40px" }}>
        <h2>Distribuir Moedas</h2>
        <select
          value={usuarioSelecionado}
          onChange={(e) => setUsuarioSelecionado(e.target.value)}
          required
          style={{ display: "block", marginBottom: "10px", padding: "8px", width: "300px" }}
        >
          <option value="">Selecione um usuário</option>
          {usuarios.map((usuario) => (
            <option key={usuario.id} value={usuario.id}>
              {usuario.nome}
            </option>
          ))}
        </select>
        {errors.alunoId && (
          <span style={{ color: "red", marginBottom: "10px", fontSize: "0.9em", display: "block" }}>{errors.alunoId}</span>
        )}
        <input
          type="number"
          placeholder="Quantidade"
          value={quantidade}
          onChange={(e) => setQuantidade(Number(e.target.value))}
          style={{ display: "block", marginBottom: "10px", padding: "8px", width: "300px" }}
          required
        />
        {errors.quantidade && (
          <span style={{ color: "red", marginBottom: "10px", fontSize: "0.9em", display: "block" }}>{errors.quantidade}</span>
        )}
        <input
          type="text"
          placeholder="Motivo"
          value={motivo}
          onChange={(e) => setMotivo(e.target.value)}
          style={{ display: "block", marginBottom: "10px", padding: "8px", width: "300px" }}
          required
        />
        {errors.motivo && (
          <span style={{ color: "red", marginBottom: "10px", fontSize: "0.9em", display: "block" }}>{errors.motivo}</span>
        )}
        {/* Mensagem de erro geral */}
        {errors.erro && (
          <span style={{ color: "red", marginBottom: "10px", fontSize: "0.9em", display: "block" }}>{errors.erro}</span>
        )}
        <button type="submit" style={{ padding: "10px", backgroundColor: "#4CAF50", color: "white", width: "300px" }}>
          Enviar Moedas
        </button>
      </form>

      {/* Histórico */}
      <h2>Histórico de Envios</h2>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid black", padding: "8px" }}>Usuário</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Quantidade</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Motivo</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Data</th>
          </tr>
        </thead>
        <tbody>
          {historico.map((item) => {
            // Busca o nome do usuário pelo destinoId
            let nomeUsuario = "Desconhecido";
            const usuarioEncontrado = usuarios.find((u) => u.id === item.destinoId);
            if (usuarioEncontrado) {
              nomeUsuario = usuarioEncontrado.nome;
            }

            let dataFormatada = "Data não disponível";
            if (item.data) {
              const data = new Date(item.data);
              if (!isNaN(data.getTime())) {
                dataFormatada = data.toLocaleString("pt-BR", {
                  day: "2-digit",
                  month: "2-digit",
                  year: "numeric",
                  hour: "2-digit",
                  minute: "2-digit",
                  second: "2-digit"
                });
              }
            }

            return (
              <tr key={item.id}>
                <td style={{ border: "1px solid black", padding: "8px" }}>{nomeUsuario}</td>
                <td style={{ border: "1px solid black", padding: "8px" }}>{item.quantidade}</td>
                <td style={{ border: "1px solid black", padding: "8px" }}>{item.motivo}</td>
                <td style={{ border: "1px solid black", padding: "8px" }}>{dataFormatada}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

export default DashboardAdmin;