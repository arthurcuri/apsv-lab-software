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
  const [errors, setErrors] = useState<{ quantidade?: string; motivo?: string; alunoId?: string; erro?: string }>({});
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
    setErrors({}); // Limpa erros anteriores

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
    } catch (error: any) {
      // Se o back-end retornar um objeto de erros de validação
      if (error.response && error.response.status === 400 && error.response.data) {
        // Pode ser um objeto { campo: mensagem } ou { erro: mensagem }
        setErrors(error.response.data);
      } else {
        alert("Erro ao distribuir moedas.");
      }
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Dashboard do Professor</h1>

      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
        
        {/* Histórico lado esquerdo */}
        <div style={{ flex: 1 }}>
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
                const aluno = alunos.find((a) => a.id === item.destinoId);
                const nomeAluno = aluno ? aluno.nome : "Desconhecido";
                const data = new Date(item.data);
                const dataFormatada = !isNaN(data.getTime())
                  ? data.toLocaleString("pt-BR", {
                      day: "2-digit",
                      month: "2-digit",
                      year: "numeric",
                      hour: "2-digit",
                      minute: "2-digit",
                      second: "2-digit"
                    })
                  : "Data não disponível";

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

        {/* Lado direito: Saldo e Formulário */}
        <div style={{ width: "350px", marginLeft: "20px" }}>
          
          {/* Saldo */}
          <div
            style={{
              backgroundColor: "#f0f0f0",
              padding: "15px",
              borderRadius: "8px",
              marginBottom: "20px",
              textAlign: "center",
              fontSize: "18px",
              color: "#000"
            }}
          >
            <strong>Saldo de Moedas:</strong> {saldo}
          </div>

          {/* Formulário */}
          <form onSubmit={handleDistribuirMoedas}>
            <h2>Envio Rápido</h2>
            <select
              value={alunoSelecionado}
              onChange={(e) => setAlunoSelecionado(e.target.value)}
              required
              style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
            >
              <option value="">Selecione um aluno</option>
              {alunos.map((aluno) => (
                <option key={aluno.id} value={aluno.id}>
                  {aluno.nome}
                </option>
              ))}
            </select>
            {errors.alunoId && (
              <span style={{ color: "red", fontSize: "0.9em" }}>{errors.alunoId}</span>
            )}

            <input
              type="number"
              placeholder="Quantidade"
              value={quantidade}
              onChange={(e) => setQuantidade(Number(e.target.value))}
              style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
            />
            {errors.quantidade && (
              <span style={{ color: "red", fontSize: "0.9em" }}>{errors.quantidade}</span>
            )}

            <input
              type="text"
              placeholder="Motivo"
              value={motivo}
              onChange={(e) => setMotivo(e.target.value)}
              style={{ display: "block", marginBottom: "10px", padding: "8px", width: "100%" }}
            />
            {errors.motivo && (
              <span style={{ color: "red", fontSize: "0.9em" }}>{errors.motivo}</span>
            )}

            {errors.erro && (
              <span style={{ color: "red", fontSize: "0.9em" }}>{errors.erro}</span>
            )}

            <button
              type="submit"
              style={{
                padding: "10px",
                backgroundColor: "#4CAF50",
                color: "white",
                width: "100%",
                border: "none",
                borderRadius: "8px",
                cursor: "pointer"
              }}
            >
              Realizar Transação
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default DashboardProfessorPage;