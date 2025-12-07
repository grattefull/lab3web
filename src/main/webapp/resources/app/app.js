

//  часики
function updateClock(id) {
    const e = document.getElementById(id);
    if (!e) return; // выхожу

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

    const centerX = rect.width / 2;   //  х в середине свг
    const centerY = rect.height / 2;  //  y в середине свг
    const scalePx = rect.width / 3;   //

    let x = ((px - centerX) / scalePx) * r;
    let y = ((centerY - py) / scalePx) * r;

    if (Math.abs(x) < 1e-6) x = 0;
    if (Math.abs(y) < 1e-6) y = 0;

    // тут бэк
    const gx = document.getElementById("mainForm:graphX");
    if (gx) gx.value = x;

    const gy = document.getElementById("mainForm:graphY");
    if (gy) gy.value = y;

    // тут визуал
    const xInput = document.getElementById("mainForm:x");
    if (xInput) xInput.value = x.toFixed(3);   // в окне не на серваке

    const yHidden = document.getElementById("mainForm:yValue");
    if (yHidden) yHidden.value = y;

    const yOut = document.getElementById("mainForm:yOut");
    if (yOut) yOut.textContent = y.toFixed(3);

    const ySlider = document.getElementById("ySlider");
    if (ySlider) ySlider.value = y;

    const hiddenSubmit = document.getElementById("mainForm:hiddenSubmit");
    if (hiddenSubmit) hiddenSubmit.click();
}







    document.addEventListener("DOMContentLoaded", function () {
    // часы если есть элемент #clock
    if (document.getElementById("clock")) {
        updateClock("clock");
        setInterval(function () {
            updateClock("clock");
        }, 13000);
    }


});
