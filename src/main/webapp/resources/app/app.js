

//  часики
function updateClock(id) {
    const e = document.getElementById(id);
    if (!e) return;

    const d = new Date();
    const s =
        d.getFullYear() + "-" +
        String(d.getMonth() + 1).padStart(2, "0") + "-" +
        String(d.getDate()).padStart(2, "0") + " " +
        String(d.getHours()).padStart(2, "0") + ":" +
        String(d.getMinutes()).padStart(2, "0") + ":" +
        String(d.getSeconds()).padStart(2, "0");

    e.textContent = s;
}


function handleGraphClick(e) {
    const svg = document.getElementById("graphSvg");
    const msg = document.getElementById("message");
    if (!svg) return;

    function show(text) {
        if (!msg) return;
        msg.textContent = text || "";
        msg.style.color = text ? "red" : "";
    }


    const rEl = document.getElementById("mainForm:rValue");
    const r = parseFloat((rEl && rEl.value) || "NaN");

    if (!Number.isFinite(r) || r < 1) {
        show("Сначала выберите R");
        return;
    }
    show("");

    const rect = svg.getBoundingClientRect();
    const px = e.clientX - rect.left;
    const py = e.clientY - rect.top;


    const centerX = rect.width / 2;
    const centerY = rect.height / 2;
    const scalePx = rect.width / 3;

    // перевожу пиксели в координаты
    let x = ((px - centerX) / scalePx) * r;
    let yReal = ((centerY - py) / scalePx) * r;

    // убираем -0
    if (Math.abs(x) < 1e-6) x = 0;
    if (Math.abs(yReal) < 1e-6) yReal = 0;

    // тут выбираб ближайший
    const allowedY = [-4, -3, -2, -1, 0, 1, 2, 3, 4];
    let y = allowedY[0];
    let minDiff = Math.abs(yReal - y);
    for (let i = 1; i < allowedY.length; i++) {
        const diff = Math.abs(yReal - allowedY[i]);
        if (diff < minDiff) {
            minDiff = diff;
            y = allowedY[i];
        }
    }

    const xInput = document.getElementById("mainForm:x");
    if (xInput) xInput.value = x.toFixed(3);

    const yHidden = document.getElementById("mainForm:yValue");
    if (yHidden) yHidden.value = y;

    const yOut = document.getElementById("mainForm:yOut");
    if (yOut) yOut.textContent = String(y);

    const ySlider = document.getElementById("ySlider");
    if (ySlider) ySlider.value = y;

    // жмву скрытую кнопку
    const hiddenSubmit = document.getElementById("mainForm:hiddenSubmit");
    if (hiddenSubmit) hiddenSubmit.click();
}





document.addEventListener("DOMContentLoaded", function () {
    // часы, если есть элемент #clock
    if (document.getElementById("clock")) {
        updateClock("clock");
        setInterval(function () {
            updateClock("clock");
        }, 13000);
    }


});
