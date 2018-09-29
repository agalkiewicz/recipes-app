export class Recipe {
  id: number;
  title: string;
  ingredients: string[];
  url: string;
  image: string;
  description: string;
  instructions: string[];
  categories: string[];


  constructor(title: string, url: string) {
    this.title = title;
    this.url = url;
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
