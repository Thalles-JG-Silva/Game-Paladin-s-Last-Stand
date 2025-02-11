# Paladin's Last Stand 🏜️🧟‍♂️

## 📜 Descrição

**Paladin's Last Stand** é um jogo de plataforma 2D onde o jogador assume o papel de um Paladino que deve sobreviver a uma jornada pelo deserto infestado de **zumbis**. O objetivo é evitar os mortos-vivos, coletar moedas e usar poderes sagrados para derrotar os inimigos enquanto percorre um cenário árido e hostil.

O jogo apresenta animações detalhadas para os personagens, uma trilha sonora imersiva e uma jogabilidade dinâmica baseada em saltos e ataques mágicos. O jogador precisa sobreviver coletando itens e destruindo os zumbis que bloqueiam seu caminho.

---

## 🎮 Como Jogar

- **Enter** → Inicia o jogo no menu principal.
- **S ou ESC** → Sai do jogo no menu principal.
- **Espaço** → Faz o Paladino pular sobre os obstáculos.
- **X** → Dispara um **poder sagrado** para destruir os zumbis (caso tenha poderes disponíveis).

### 🕹️ Estados do Jogo:
1. **Menu Principal** → Aguarda o jogador iniciar a partida.
2. **Jogo Rodando** → O jogador corre automaticamente pelo deserto, deve pular, coletar moedas e usar poderes.
3. **Game Over** → O jogo reinicia após um tempo, retornando ao menu principal.

---

## 📁 Estrutura do Projeto

```plaintext
📂 PaladinLastStand/
│-- 📂 assets/              # Contém imagens e músicas do jogo
│   ├── 📂 Imagens/         # Sprites dos personagens e fundo do jogo
│   ├── 📂 Musicas/         # Trilha sonora e efeitos sonoros
│-- 📂 src/                 # Código-fonte do jogo
│   ├── Background.java     # Gerencia o fundo animado do deserto
│   ├── Game.java           # Inicializa o jogo e a janela principal
│   ├── GamePanel.java      # Controla a lógica principal do jogo
│   ├── Moeda.java          # Classe responsável pelas moedas coletáveis
│   ├── Obstacle.java       # Representa os zumbis (fêmea e macho)
│   ├── Player.java         # Gerencia o personagem principal (Paladino)
│   ├── PlayerKeyListener.java  # Captura os eventos do teclado
│   ├── Power.java          # Define os poderes sagrados lançados pelo Paladino
│   ├── PowerItem.java      # Representa os itens de poder coletáveis
│   ├── SoundManager.java   # Gerencia os efeitos sonoros e a música de fundo
│-- README.md               # Informações sobre o jogo e como executar
```

---

## 🛠️ Tecnologias Utilizadas

- **Java (Swing e AWT)** → Para interface gráfica e lógica do jogo.
- **Java Sound API** → Para efeitos sonoros e música de fundo.
- **Sprites e Animações** → Utilização de múltiplos frames para criar fluidez nos movimentos dos personagens.

---

## 🚀 Como Executar

1. **Clone o repositório**:

   ```sh
   git clone https://github.com/Thalles-JG-Silva/Game-Paladin-s-Last-Stand.git
   ```

2. **Compile os arquivos Java**:

   ```sh
   cd PaladinLastStand/src
   javac *.java
   ```

3. **Execute o jogo**:

   ```sh
   java Game
   ```

---

## 🖼️ Imagens e Recursos

- **Cenário do deserto** animado para criar a sensação de movimento.
- **Zumbis macho e fêmea** como obstáculos que tentam deter o Paladino.
- **Moedas e itens de poder** espalhados pelo cenário.
- **Efeitos sonoros e trilha sonora temática** para maior imersão.

---

## 📌 Funcionalidades Implementadas

✔️ **Fundo animado** com efeito de scrolling.  
✔️ **Paladino com animações de corrida e pulo.**  
✔️ **Zumbis animados** que servem como obstáculos.  
✔️ **Sistema de colisão** entre jogador, zumbis, moedas e poderes.  
✔️ **Poder sagrado** que destrói zumbis ao ser disparado.  
✔️ **Trilha sonora e efeitos sonoros temáticos.**  
✔️ **Interface simples e responsiva.**  

---

## 🎯 Melhorias Futuras

- **Dificuldade progressiva**: aumento da velocidade e frequência de zumbis.  
- **Novos tipos de inimigos** além dos zumbis.  
- **Diferentes poderes** para o Paladino.  
- **Sistema de pontuação e ranking** para aumentar o desafio.  

---

🔥 **Sobreviva ao apocalipse zumbi no deserto! Torne-se o último Paladino de pé!** 🔥