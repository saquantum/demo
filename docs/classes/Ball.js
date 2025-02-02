export default class Ball {
    static img = null;
    // set initial position and travel direction
    constructor(posX0, posY0, direction) {
        this.posX0 = posX0;
        this.posY0 = posY0;
        this.direction = direction;
        // save the two for better peformance
        this.travelX = Math.cos(direction);
        this.travelY = Math.sin(direction);
        // set initial travel length to 0
        this.travelLength = 0;
        // placeholder
        this._posX = this.posX0;
        this._posY = this.posY0;
        this.alive = false;
    }
    // get and set current position
    get posX() {
        return this._posX;
    }
    set posX(_posX) {
        this._posX = _posX;
    }
    get posY() {
        return this._posY;
    }
    set posY(_posY) {
        this._posY = _posY;
    }

    setDirection(direction){
        this.direction = direction;
        this.travelX = Math.cos(direction);
        this.travelY = Math.sin(direction);
    }

    // load the image
    static preload() {
        if (!Ball.img) {
            Ball.img = loadImage("./images/bullet.png"); 
        }
    }
    
    // has the ball travelled long enough?
    hasGoneTooFar() {
        let limit = 2000; // a temporary magic number
        if ((this._posX - this.posX0) ** 2 + (this._posY - this.posY0) ** 2 >= limit) {
            this.alive = false;
        }
    }

    // update ball's position every p5 cycle
    updateBall() {
        if (!this.alive) {
            return;
        }
        this._posX = this.posX0 + this.travelLength * this.travelX;
        this._posY = this.posY0 + this.travelLength * this.travelY;
        this.travelLength++;
        this.hasGoneTooFar();
    }

    // activate and reset parameters of the ball
    activate(){
        this.alive = true;
        this.travelLength = 0; 
        this._posX = this.posX0;
        this._posY = this.posY0;
    }

    // to draw the ball on the canvas
    plot() {
        if (!this.alive) {
            return;
        }
        // store current coordinate
        push();
        // set temporary coordinate origin
        translate(this._posX - width / 2, this._posY - height / 2);
        // rotate
        rotate(this.direction);
        // to draw the image centered at temporary coordinate instead of left top corner
        imageMode(CENTER);
        // draw the image under temporary coordinate
        image(Ball.img, 0, 0, 40, 40);
        // restore coordinate
        pop();
    }
}