/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          bg: '#0f172a',        // Fundo principal da página (Navy escuro)
          surface: '#1e293b',   // Fundo dos Cards e Formulários (Navy intermediário)
          border: '#334155',    // Bordas sutis para separar elementos no escuro
          text: '#f8fafc',      // Texto principal (Branco/Cinza suave)
          primary: '#06b6d4',   // Cyan chamativo para botões e links
          hover: '#0891b2',     // Cyan para o passar do mouse
        }
      },
    },
  },
  plugins: [],
}

