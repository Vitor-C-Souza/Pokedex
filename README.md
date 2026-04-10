# 📱 Pokedex App

Uma aplicação Android moderna para explorar o universo Pokémon, utilizando
a [PokeAPI](https://pokeapi.co/). O projeto demonstra o uso de tecnologias atuais do ecossistema
Android, seguindo os princípios de Clean Architecture e as melhores práticas de desenvolvimento.

## 🚀 Funcionalidades

- **Listagem de Pokémon:** Navegação fluida por todos os Pokémon.
- **Busca Avançada:** Pesquisa por nome ou ID.
- **Detalhes Completos:**
    - **Sobre:** Descrição, altura e peso.
    - **Base Stats:** Atributos visuais (HP, Ataque, Defesa, etc.).
    - **Evoluções:** Cadeia evolutiva completa com condições de evolução.
    - **Movimentos:** Lista detalhada de golpes que o Pokémon pode aprender.
- **Sistema de Favoritos:** Salve seus Pokémon preferidos localmente (Room Database).
- **Filtros de Movimentos:** Na tela "All Moves", os golpes são organizados por:
    1. Método de aprendizado (Level-up primeiro).
    2. Nível (ordem crescente).
    3. Nome (ordem alfabética).
- **Busca de Movimentos:** Filtre os golpes por nome ou tipo.

## 🛠️ Tecnologias e Bibliotecas

- **[Kotlin](https://kotlinlang.org/):** Linguagem principal.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose):** UI declarativa e moderna.
- *
  *[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html):
  ** Separação clara de responsabilidades (Data, Domain, UI).
- **[Koin](https://insert-koin.io/):** Injeção de dependência leve e eficiente.
- **[Retrofit](https://square.github.io/retrofit/):** Cliente HTTP para consumo da PokeAPI.
- **[Room](https://developer.android.com/training/data-storage/room):** Persistência de dados local
  para favoritos e cache.
- **[Coil](https://coil-kt.github.io/coil/):** Carregamento de imagens assíncrono.
- **[Coroutines & Flow](https://developer.android.com/kotlin/coroutines):** Gerenciamento de tarefas
  assíncronas e fluxos de dados reativos.
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation):** Navegação
  entre telas.

## 📐 Arquitetura

O projeto está estruturado em três camadas principais:

1. **Data:** Implementação de repositórios, fontes de dados (API e Banco de Dados) e Mappers.
2. **Domain:** Regras de negócio, modelos de domínio e Use Cases.
3. **UI (Presentation):** Componentes Compose, ViewModels e gerenciamento de estado.

## 📸 Screenshots

*(Espaço reservado para adicionar imagens do projeto)*

## ⚙️ Como executar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/pokedex-android.git
   ```
2. Abra o projeto no **Android Studio (Ladybug ou superior)**.
3. Sincronize o Gradle.
4. Execute o app em um emulador ou dispositivo físico.

---
Desenvolvido por [Vitor Souza](https://github.com/vitorcsouza) 🚀
