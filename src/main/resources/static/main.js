const options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0,
};

function success(pos) {
    const crd = pos.coords;

    console.log("Your current position is:");
    console.log(`Latitude : ${crd.latitude}`);
    console.log(`Longitude: ${crd.longitude}`);
    console.log(`More or less ${crd.accuracy} meters.`);
    let href = window.location.href;
    if(!href.includes("lat") || !href.includes("long")) {
        href += `?lat=${crd.latitude}&long=${crd.longitude}`;
        window.location.replace(href);
    }
}

function error(err) {
    console.warn(`ERROR(${err.code}): ${err.message}`);
}

navigator.geolocation.getCurrentPosition(success, error, options);
//------------------
console.log("start register service worker");
if ('serviceWorker' in navigator) {
    navigator.serviceWorker
        .register('./service-worker.js', {scope: "./"})
        .then(function (reg) {
            console.log('Registration successful: ' + reg.scope);
        })
        .catch(function (err) {
            console.error('Registration failed: ' + err);
        });
}
console.log("end register service worker");

