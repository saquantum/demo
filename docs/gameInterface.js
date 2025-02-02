import Ball from './classes/Ball.js';
import preload from './preload.js';

window.preload = preload;

let b = new Ball(200, 200, 0);

window.setup = function () {
    createCanvas(400, 400, WEBGL);

    let vueDiv = document.createElement("div");
    vueDiv.id = "vueapp"; 
    document.body.appendChild(vueDiv);

    Vue.createApp({
        data() {
            return {
                inputAngle: ""
            };
        },
        methods: {
            updateDirection() {
                let angle = parseFloat(this.inputAngle);
                if (!isNaN(angle)) {
                    b.setDirection(angle);
                    b.activate();
                }
            }
        },
        template: `
            <div>
                <input id="text-input" type="text" v-model="inputAngle" placeholder="Enter an angle">
                <button style="position: absolute; top: 1px; left: 1px;" @click="updateDirection">set</button>
                <p>Current angle: {{ inputAngle }}</p>
            </div>
        `
    }).mount("#vueapp");
};

window.draw = function () {
    background(200);

    if (b.alive) {
        b.plot();
        b.updateBall();
    }
}
