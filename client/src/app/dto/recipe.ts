export class Recipe {
  id: number;
  title: string;
  ingredients: string[];

  constructor(id: number, title: string, ingredients: string[]) {
    this.id = id;
    this.title = title;
    this.ingredients = ingredients;
  }

  public toString = (): string => {
    let word = `{id: ${this.id}\n
           title: ${this.title}\n
           ingredients: [\n`;
    // noinspection TsLint
    for (let ingredient of this.ingredients) {
      word += `ingredient: ${ingredient}`;
      if (ingredient === this.ingredients[this.ingredients.length - 1]) {
        word += `,\n`;
      } else {
        word += `\n}`;
      }
    }
    return word;
  }
}
