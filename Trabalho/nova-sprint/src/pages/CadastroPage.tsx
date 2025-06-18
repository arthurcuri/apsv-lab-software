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
      
      // Verifica se é erro de validação do Bean Validation (status 500 com ConstraintViolationException)
      if (error.response && error.response.status === 500 && error.response.data) {
        const errorData = error.response.data;
        
        // Verifica se é erro de validação
        if (errorData.message && errorData.message.includes("ConstraintViolationException")) {
          console.log("=== ERRO DE VALIDAÇÃO DETECTADO ===");
          console.log("Mensagem completa do erro:", errorData.message);
          
          // Múltiplos padrões de regex para capturar diferentes formatos
          const patterns = [
            // Padrão 1: ConstraintViolationImpl{interpolatedMessage='mensagem'
            /ConstraintViolationImpl\{interpolatedMessage='([^']+)'/g,
            // Padrão 2: interpolatedMessage='mensagem'
            /interpolatedMessage='([^']+)'/g,
            // Padrão 3: message='mensagem'
            /message='([^']+)'/g,
            // Padrão 4: Mensagem direta após ConstraintViolation
            /ConstraintViolation.*?message='([^']+)'/g,
            // Padrão 5: Formato alternativo
            /violation.*?message:\s*'([^']+)'/gi
          ];
          
          const validationErrors = new Set(); // Usar Set para evitar duplicatas
          let foundMessages = false;
          
          // Tenta cada padrão
          patterns.forEach((pattern, index) => {
            console.log(`Tentando padrão ${index + 1}:`, pattern.source);
            const matches = [...errorData.message.matchAll(pattern)];
            
            if (matches.length > 0) {
              console.log(`Padrão ${index + 1} encontrou ${matches.length} mensagens`);
              matches.forEach(match => {
                validationErrors.add(match[1]);
                foundMessages = true;
              });
            } else {
              console.log(`Padrão ${index + 1} não encontrou nada`);
            }
          });
          
          // Se não encontrou com regex, tenta buscar por palavras-chave comuns
          if (!foundMessages) {
            console.log("Tentando busca por palavras-chave...");
            const keywordPatterns = [
              /é obrigatório/gi,
              /inválido/gi,
              /deve ter/gi,
              /não pode/gi,
              /deve ser/gi,
              /formato/gi
            ];
            
            keywordPatterns.forEach(pattern => {
              const match = errorData.message.match(pattern);
              if (match) {
                // Tenta extrair a frase completa em torno da palavra-chave
                const context = errorData.message.substring(
                  Math.max(0, match.index - 50),
                  Math.min(errorData.message.length, match.index + 100)
                );
                console.log("Contexto encontrado:", context);
              }
            });
          }
          
          const uniqueErrors = Array.from(validationErrors);
          
          if (uniqueErrors.length > 0) {
            console.log("Erros de validação encontrados:");
            uniqueErrors.forEach((errorMsg, index) => {
              console.log(`${index + 1}. ${errorMsg}`);
            });
            
            // Exibe uma mensagem mais amigável para o usuário
            const errorMessage = uniqueErrors.length === 1 
              ? uniqueErrors[0]
              : `Foram encontrados ${uniqueErrors.length} erros de validação:\n${uniqueErrors.map((msg, i) => `${i + 1}. ${msg}`).join('\n')}`;
            
            alert(errorMessage);
          } else {
            console.log("❌ Erro de validação detectado, mas não foi possível extrair as mensagens específicas");
            console.log("Tentando extração de backup...");
            
            // Extração de backup - busca por linhas que contenham mensagens típicas
            const backupMessages: string[] = [];
            const lines = errorData.message.split('\n');
            
            lines.forEach((line: string) => {
              if (line.includes('message') || 
                  line.includes('obrigatório') || 
                  line.includes('inválido') ||
                  line.includes('deve ter') ||
                  line.includes('não pode')) {
                console.log("Linha suspeita encontrada:", line.trim());
                backupMessages.push(line.trim());
              }
            });
            
            if (backupMessages.length > 0) {
              alert(`Erro de validação:\n${backupMessages.slice(0, 3).join('\n')}`);
            } else {
              alert("Erro de validação nos dados fornecidos. Verifique as informações e tente novamente.");
            }
          }
          
          console.log("=== FIM DO LOG DE VALIDAÇÃO ===");
        } else {
          // Outros erros 500
          console.log("Erro interno do servidor:", errorData.message || "Erro desconhecido");
          alert("Erro interno do servidor: " + (errorData.message || "Erro desconhecido"));
        }
      } else if (error.response && error.response.status === 400 && error.response.data) {
        // Erros de validação customizada (como os que já existiam)
        console.log("Erro de validação customizada:", error.response.data);
        alert("Erro de validação: " + JSON.stringify(error.response.data));
      } else {
        // Outros tipos de erro
        console.log("Erro genérico:", error.message || "Erro desconhecido");
        alert("Erro ao cadastrar usuário: " + (error.message || "Erro desconhecido"));
      }
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