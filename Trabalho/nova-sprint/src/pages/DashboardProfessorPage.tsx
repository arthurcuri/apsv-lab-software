import { useEffect, useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";


interface Aluno {
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

function DashboardProfessorPage() {
  const [saldo, setSaldo] = useState(0);
  const [alunos, setAlunos] = useState<Aluno[]>([]);
  const [alunoSelecionado, setAlunoSelecionado] = useState("");
  const [quantidade, setQuantidade] = useState(0);
  const [motivo, setMotivo] = useState("");
  const [historico, setHistorico] = useState<Historico[]>([]);
  const navigate = useNavigate();

  const usuarioId = sessionStorage.getItem("usuarioId");

  useEffect(() => {
    if (usuarioId) {
      buscarSaldo();
      buscarAlunos();
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

  const buscarAlunos = async () => {
    try {
      const response = await api.get("/usuarios/alunos");
      setAlunos(response.data);
    } catch (error) {
      console.error("Erro ao buscar alunos:", error);
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

    try {
      await api.post(`/usuarios/${usuarioId}/distribuir-moedas`, {
        alunoId: alunoSelecionado,
        quantidade,
        motivo
      });
      alert("Moedas distribuídas com sucesso!");
      buscarSaldo();
      buscarHistorico();
      setAlunoSelecionado("");
      setQuantidade(0);
      setMotivo("");
    } catch (error) {
      console.error("Erro ao distribuir moedas:", error);
      alert("Erro ao distribuir moedas.");
    }
  };


  return (
    <div>
      <h1>Dashboard do Professor</h1>

      {/* Saldo */}
      <div style={{ marginBottom: "20px", fontSize: "18px" }}>
        <strong>Saldo de Moedas:</strong> {saldo}
      </div>

      {/* Formulário */}
      <form onSubmit={handleDistribuirMoedas} style={{ marginBottom: "40px" }}>
        <h2>Distribuir Moedas</h2>
        <select
          value={alunoSelecionado}
          onChange={(e) => setAlunoSelecionado(e.target.value)}
          required
          style={{ display: "block", marginBottom: "10px", padding: "8px", width: "300px" }}
        >
          <option value="">Selecione um aluno</option>
          {alunos.map((aluno) => (
            <option key={aluno.id} value={aluno.id}>
              {aluno.nome}
            </option>
          ))}
        </select>
        <input
          type="number"
          placeholder="Quantidade"
          value={quantidade}
          onChange={(e) => setQuantidade(Number(e.target.value))}
          style={{ display: "block", marginBottom: "10px", padding: "8px", width: "300px" }}
          required
        />
        <input
          type="text"
          placeholder="Motivo"
          value={motivo}
          onChange={(e) => setMotivo(e.target.value)}
          style={{ display: "block", marginBottom: "10px", padding: "8px", width: "300px" }}
          required
        />
        <button type="submit" style={{ padding: "10px", backgroundColor: "#4CAF50", color: "white", width: "300px" }}>
          Enviar Moedas
        </button>
      </form>

      {/* Histórico */}
      <h2>Histórico de Envios</h2>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid black", padding: "8px" }}>Aluno</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Quantidade</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Motivo</th>
            <th style={{ border: "1px solid black", padding: "8px" }}>Data</th>
          </tr>
        </thead>
        <tbody>
          {historico.map((item) => {
            // Busca o nome do aluno pelo destinoId
            let nomeAluno = "Desconhecido";
            const alunoEncontrado = alunos.find((a) => a.id === item.destinoId);
            if (alunoEncontrado) {
              nomeAluno = alunoEncontrado.nome;
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
                <td style={{ border: "1px solid black", padding: "8px" }}>{nomeAluno}</td>
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

export default DashboardProfessorPage;