import { loginService } from "@/services/AuthService";
import { useState } from "react";

export function Login() {
  const [identity, setIdentity] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setErrorMessage("");

    try {
        const data = await loginService(identity, password);
        localStorage.setItem('@medicalfield-rescuer:token', data.token)
    } catch (error) {
        setErrorMessage(error.message);
    } finally {
        setIsLoading(false);
    }
    
  };

  return (
    <div className="h-screen flex justify-center items-center px-4">
      <div className="w-full max-w-md md:w-8/12 lg:w-4/12 bg-brand-surface p-8 rounded-2xl border border-slate-700/60 shadow-2xl flex flex-col justify-center transition-all">
        <div className="text-center mb-6">
          <h1 className="font-sans text-4xl font-black text-brand-primary">
            Medical<span className="text-brand-text">Field</span>
          </h1>
        </div>


        {errorMessage && (
          <div className="mb-4 p-3 bg-red-500/10 border border-red-500/30 rounded-lg text-red-400 text-xs font-mono text-center">
            {errorMessage}
          </div>
        )}



        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div className="flex flex-col gap-1.5">
            <label className="font-mono text-xs uppercase text-slate-400">
              CRM / COREN
            </label>
            <input
              type="text"
              required
              value={identity}
              onChange={(e) => setIdentity(e.target.value)}
              placeholder="CRM-RS 123456"
              className="w-full bg-slate-900/70 border border-slate-700 rounded-lg px-4 py-3 text-brand-text placeholder-slate-500 focus:outline-none focus:border-brand-primary focus:ring-1 focus:ring-brand-primary transition-all font-sans"
            />
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="font-mono text-xs uppercase text-slate-400">
              Senha de Acesso
            </label>
            <input
              type="password"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
              className="w-full bg-slate-900/70 border border-slate-700 rounded-lg px-4 py-3 text-brand-text placeholder-slate-500 focus:outline-none focus:border-brand-primary focus:ring-1 focus:ring-brand-primary transition-all font-sans"
            />
          </div>

          <button
            type="submit"
            disabled={isLoading}
            className="mt-2 w-full bg-brand-primary hover:bg-brand-hover text-white font-bold py-3.5 px-4 rounded-lg shadow-lg shadow-brand-primary/20 active:scale-[0.98] transition-all flex items-center justify-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {isLoading ? (
              <>
                <span className="h-4 w-4 border-2 border-white border-t-transparent rounded-full animate-spin"></span>
                <span>Autenticando...</span>
              </>
            ) : (
              <span>Iniciar Plantão</span>
            )}
          </button>
        </form>
      </div>
    </div>
  );
}
