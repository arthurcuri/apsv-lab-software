import { Routes, Route, useLocation } from 'react-router-dom'; // IMPORTANTE: useLocation
import Sidebar from './components/Sidebar';
import SidebarAluno from "./components/SidebarAluno";
import SidebarEmpresa from "./components/SidebarEmpresa";
import SidebarProfessor from "./components/SidebarProfessor";

import LoginPage from './pages/LoginPage';
import CadastroPage from './pages/CadastroPage';
import RecuperarSenhaPage from './pages/RecuperarSenhaPage';
import DashboardProfessorPage from './pages/DashboardProfessorPage';
import DashboardAlunoPage from './pages/DashboardAlunoPage';
import ExtratoPage from './pages/ExtratoPage';
import CadastroVantagemPage from './pages/CadastroVantagemPage';
import ResgateVantagemPage from './pages/ResgateVantagemPage';
import DashboardAdmin from './pages/DashboardAdmin';

function App() {
  const location = useLocation();

  const paginasSemSidebar = ['/', '/cadastro', '/recuperar-senha'];

  const exibirSidebar = !paginasSemSidebar.includes(location.pathname);

  // Pega o tipo de usuário do sessionStorage
  const usuarioTipo = sessionStorage.getItem("usuarioTipo");

  // Função para escolher a sidebar correta
  const renderSidebar = () => {
    if (!exibirSidebar) return null;
    if (usuarioTipo === "ALUNO") return <SidebarAluno />;
    if (usuarioTipo === "PROFESSOR") return <SidebarProfessor />;
    if (usuarioTipo === "EMPRESA") return <SidebarEmpresa />;
    return <Sidebar />; // Sidebar padrão
  };

  return (
    <div style={{ display: 'flex' }}>
      {renderSidebar()}
      <div style={{ marginLeft: exibirSidebar ? '270px' : '0', padding: '20px', width: '100%' }}>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/cadastro" element={<CadastroPage />} />
          <Route path="/recuperar-senha" element={<RecuperarSenhaPage />} />
          <Route path="/dashboard-professor" element={<DashboardProfessorPage />} />
          <Route path="/dashboard-aluno" element={<DashboardAlunoPage />} />
          <Route path="/extrato" element={<ExtratoPage />} />
          <Route path="/cadastro-vantagem" element={<CadastroVantagemPage />} />
          <Route path="/resgate-vantagem" element={<ResgateVantagemPage />} />
          <Route path="/dashboard-admin" element={<DashboardAdmin />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;