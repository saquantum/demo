export function preloader(p5){

    const isGithubPages = window.location.hostname.includes("github.io");
    const basePath = isGithubPages ? "./assets/images/" : "../assets/images/";

    let images = new Map(); // <String name, Image img>
    images.set("Tree", p5.loadImage(basePath + "Tree.jpg"));
    images.set("Bush", p5.loadImage(basePath + "Bush.jpg"));
    images.set("Grass", p5.loadImage(basePath + "Grass.jpg"));
    return images;
}
