async function compartilharOrcamento() {
    const ok = calcular();
    if (!ok) return;

    const mensagem = montarMensagemOrcamento();

    if (typeof AndroidShare !== 'undefined') {
        AndroidShare.share(mensagem);
        return;
    }

    if (navigator.share) {
        await navigator.share({ title: "Orçamento de m²", text: mensagem });
    }
}
