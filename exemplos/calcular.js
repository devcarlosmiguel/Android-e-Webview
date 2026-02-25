function calcular() {
    const largura = parseNumber(larguraEl.value);
    const altura = parseNumber(alturaEl.value);
    const valorM2 = parseNumber(valorEl.value);

    let area = largura * altura;
    let preco = area * valorM2;

    const mode = roundModeEl.value;
    if (mode === '2') preco = parseFloat(preco.toFixed(2));
    else if (mode === 'ceil') preco = Math.ceil(preco);

    areaValueEl.textContent = formatNumber(area, 2);
    priceValueEl.textContent = formatNumber(preco, mode === 'ceil' ? 0 : 2);
}
