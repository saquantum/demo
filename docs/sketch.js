let img;

let canvasX = 1280;
let canvasY = 720;

function preload() {
  img = loadImage("back.webp");
}

let SxSlider, SySlider, spanSlider, rotSlider, HySlider;

function setup() {
  createCanvas(canvasX, canvasY, WEBGL);
  
  createSliderWithLabel("Sx: ", 0.1, 1.0, 0.5 / sqrt(2), 0.01, (slider) => (SxSlider = slider));
  createSliderWithLabel("Sy: ", 0.1, 1.0, 0.5 / sqrt(2), 0.01, (slider) => (SySlider = slider));
  createSliderWithLabel("Span: ", 0, PI, PI / 2, 0.01, (slider) => (spanSlider = slider));
  createSliderWithLabel("Rot: ", 0, 2*PI, PI / 4, 0.01, (slider) => (rotSlider = slider));
  createSliderWithLabel("Hy: ", 0.01, 2.0, 1.0, 0.01, (slider) => (HySlider = slider));
}

function draw() {
  background(200);
  stroke(0); // Black border
  strokeWeight(2); // Line thickness

  let Sx = SxSlider.value();
  let Sy = SySlider.value();
  let span = spanSlider.value();
  let rot = rotSlider.value();
  let Hy = HySlider.value();

  let gridSize = 8;
  let cellWidth = canvasX / gridSize;
  let cellHeight = canvasY / gridSize;

  // Draw grid lines
  for (let i = 0; i <= gridSize; i++) {
    let x = -canvasX / 2 + i * cellWidth;
    let y = -canvasY / 2 + i * cellHeight;

    // Apply transformation using newCoorX and newCoorY
    let x1 = newCoorX(x, -canvasY / 2, Sx, Sy, span, rot);
    let y1 = Hy * newCoorY(x, -canvasY / 2, Sx, Sy, span, rot);

    let x2 = newCoorX(x, canvasY / 2, Sx, Sy, span, rot);
    let y2 = Hy * newCoorY(x, canvasY / 2, Sx, Sy, span, rot);

    let x3 = newCoorX(-canvasX / 2, y, Sx, Sy, span, rot);
    let y3 = Hy * newCoorY(-canvasX / 2, y, Sx, Sy, span, rot);

    let x4 = newCoorX(canvasX / 2, y, Sx, Sy, span, rot);
    let y4 = Hy * newCoorY(canvasX / 2, y, Sx, Sy, span, rot);

    // Draw vertical lines
    line(x1, y1, x2, y2);

    // Draw horizontal lines
    line(x3, y3, x4, y4);
  }
}

function newCoorX(x, y, Sx, Sy, span, rot){
  let newX = x*Sx*cos(rot) + y*Sy*cos(span+rot);
  let newY = x*Sx*sin(rot) + y*Sy*sin(span+rot);
  return newX;
}

function newCoorY(x, y, Sx, Sy, span, rot){
  let newX = x*Sx*cos(rot) + y*Sy*cos(span+rot);
  let newY = x*Sx*sin(rot) + y*Sy*sin(span+rot);
  return newY;
}

function createSliderWithLabel(labelText, min, max, defaultValue, step, callback) {
  let container = createDiv();
  container.style("margin", "5px");
  let label = createSpan(labelText);
  label.style("margin-right", "5px");
  
  let slider = createSlider(min, max, defaultValue, step);
  slider.style("width", "200px");

  container.child(label);
  container.child(slider);

  callback(slider);
}
