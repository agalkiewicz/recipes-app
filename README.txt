Aplikacja składująca przepisy kulinarne ze stron internetowych

Praca jest podzielona na dwie części:
 * aplikację kliencką napisaną w Angular 5 - katalog client
 * aplikację serwerową napisaną w Spring Boot - katalog server

 Aby uruchomić aplikację kliencką, należy mieć zainstalowaną bibliotekę Node.js.
 Po wejściu w katalog client -> src należy wywołać polecenie
 npm install w celu instalacji frameworka i bibliotek zależnych z pliku package.json.
 Po instalacji należy uruchomić aplikację, wywołując polecenie ng serve.
 Aplikacja domyślnie uruchamia się na localhost:4200.

 Struktura projektu:
 client -> src -> app
  recipe, recipes, navbar, sign-in - katalogi komponentów
  _service - katalog serwisów
  _model - katalog modeli
  _module - katalog modułów
  app.module.ts - główny moduł aplikacji
  app.component.ts - główny komponent aplikacji

 Aby uruchomić aplikację serwerową, należy mieć zainstalowane narzędzie Maven.
 W celu instalacji bibliotek potrzebnych do poprawnego działania aplikacji, należy
 wywołać polecenie mvn compile. Zostaną zainstalowane wszystkie biblioteki z
 pliku pom.xml. Aby uruchomić aplikację, należy wywołać polecenie
 mvn spring-boot:run. Aplikacja uruchamia się domyślnie na localhost:8080.

 Struktura projektu:
 server -> src -> main -> java -> com -> example -> recipesapp
  auth - część aplikacji odpowiedzialna za uwierzytelnienie i autoryzację
  htmlanalysis - część aplikacji odpowiedzialna za mechanizm analizy treści
  fts - część aplikacja odpowiedzialna za mechanizm wyszukiwania
  recipe - katalog przechowujący RecipeController - główny kontroler aplikacji,
           RecipeDAO, RecipeRepository, RecipeStepRepository - klasy pośredniczące pomiędzy aplikacją a bazą danych
           Recipe, RecipeStep - modele danych

Konfiguracja bazy danych znajduje się w pliku server -> src -> main -> resources -> application.properties.
