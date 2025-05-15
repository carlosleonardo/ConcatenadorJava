# Define o comando para teste
$command = "java -cp '.\out\production\Concatenador;C:\Users\s861321135\Projetos\Java\Libs\commons-cli-1.9.0\commons-cli-1.9.0.jar' Concatenador"

# Define o tamanho máximo inicial
$maxLength = 8192  # Valor inicial, considerando o limite do Windows
$step = 100          # Incremento/decremento para testar limites
$currentLength = 1   # Tamanho inicial do argumento

# Loop para testar diferentes tamanhos
while ($currentLength -le $maxLength) {
    try {
        # Gera um argumento com o tamanho atual
        $argument = "a" * $currentLength

        # Monta e executa o comando
        $cmd = "$command $argument"
        Invoke-Expression $cmd

        # Exibe o tamanho testado
        Write-Host "`nTestado com $currentLength caracteres: Sucesso"

        # Incrementa o tamanho
        $currentLength += $step
    }
    catch {
        # Caso o comando falhe, exibe o tamanho limite
        Write-Host "Falha ao executar com $currentLength caracteres"
        Write-Host "Limite máximo suportado: $($currentLength - $step) caracteres"
        break
    }
}

Write-Host "Teste concluído."
