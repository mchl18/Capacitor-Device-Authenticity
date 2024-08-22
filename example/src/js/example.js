import { DeviceAuthenticity } from 'capacitor-device-authenticity';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    DeviceAuthenticity.echo({ value: inputValue })
}
