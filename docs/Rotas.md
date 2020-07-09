
# Rotas

<!-- vscode-markdown-toc -->
* [REST](#REST)
* [GraphQL (/graphql/get)](#GraphQL)
	* [Food](#foodgraphql)
		* [Queries:](#QueriesFood)
		* [Mutations:](#MutationsFood)
	* [Patients](#patientsget)
		* [Queries:](#QueriesPatients)
		* [Mutations:](#MutationsPatients)

<!-- vscode-markdown-toc-config
	numbering=true
	autoSave=true
	/vscode-markdown-toc-config -->
<!-- /vscode-markdown-toc -->



##  1. <a name='REST'></a>REST

### 

##  2. <a name='GraphQL'></a>GraphQL

*Bizu*: Os campos com exclamação `!` são obrigatórios, os demais podem ser omitidos se não forem relevantes para a _query_;

Todas as rotas pelo GraphQL usam a rota `/graphql/get/`

###  2.1. <a name='foodgraphql'></a>Food

####  2.1.1. <a name='QueriesFood'></a>Queries:

##### listFood
Rota:
``` 
query {
    listFood(uuidUser: String!) {
        uuid,
        foodName,
        foodGroup,
        custom,
        created,
    }
}
```

Exemplo:
```
query {
    listFood(uuidUser: "ba179e310491460ebaa7260cf355180f") {
        uuid,
        foodName,
        foodGroup,
        custom,
        created,
    }
}
```

##### listFoodPaginated
Rota:
``` 
query {
    listFoodPaginated(uuidUser: String!, indexPage: Int!, sizePage: Int!) {
        uuid,
        foodName,
        foodGroup,
        custom,
        created,
    }
}
```

Exemplo:
```
query {
    listFoodPaginated(uuidUser: "ba179e310491460ebaa7260cf355180f", indexPage: 0, sizePage: 10) {
        uuid,
        foodName,
        foodGroup,
        custom,
        created,
    }
}
```

##### searchFood
Rota:
``` 
query {
    searchFood(uuidUser: String!, partialFoodName: String!) {
        uuid,
        foodName,
        foodGroup,
        custom,
        created,
    }
}
```

Exemplo:
```
query {
    searchFood(uuidUser: "ba179e310491460ebaa7260cf355180f", partialFoodName: "arr") {
        uuid,
        foodName,
        foodGroup,
        custom,
        created,
    }
}
```

##### getUnits
Rota:
``` 
query {
    getUnits(uuidFood: String!) {
        calories,
        proteins,
        carbohydrates,
        lipids,
        fiber,
    }
}
```

Exemplo:
```
query {
    getUnits(uuidFood: "f6bb76e0e06f4fe5b8d41d958c942a48") {
		calories,
    	proteins,
    	carbohydrates,
    	lipids,
    	fiber,
    }
}
```


####  2.1.2. <a name='MutationsFood'></a>Mutations:

##### createFood
Rota:
```
mutation {
    createFood( uuidUser: String!, 
                foodInput: {
                    foodName: String!
                    foodGroup: String!
                    measureTotalGrams: Float!
                    measureType: String!
                    measureAmountValue: Float!
                },
                nutritionInput: {
                    calories: Float!
                    proteins: Float!
                    carbohydrates: Float!
                    lipids: Float!
                    fiber: Float!
                })
}
```

Exemplo:
```
mutation {
    createFood( uuidUser: "ba179e310491460ebaa7260cf355180f", 
                foodInput: {
                    foodName: "Pudim de Passas", 
                    foodGroup: "Doces",
                    measureTotalGrams: 21.0,
                    measureType: "Medidas Carteadas",
                    measureAmountValue: 110.0
                },
                nutritionInput: {
                    calories: 1.0,
                    proteins: 2.0,
                    carbohydrates: 3.0,
                    lipids: 4.0,
                    fiber: 5.0    
                })
}
```


##### customizeFood
Rota:
```
customizeFood(  uuidUser: String!, 
                uuidFood: String!,
                customInput: {
                    measureTotalGrams: Float!
                    measureType: String!
                    measureAmountValue: Float!
                }, 
              nutritionInput: {
                    calories: Float!
                    proteins: Float!
                    carbohydrates: Float!
                    lipids: Float!
                    fiber: Float!
              })
```

Exemplo:
```
mutation {
    customizeFood(  uuidUser: "ba179e310491460ebaa7260cf355180f",
                    uuidFood: "6584956257c94bc892ac8f9506087243",
                    customInput: {
                        measureTotalGrams: 999.9,
                        measureType: "Medidas Carteadas",
                        measureAmountValue: 999.9 
                    },
                    nutritionInput: {
                        calories: 9.9,
                        proteins: 9.9,
                        carbohydrates: 9.9,
                        lipids: 9.9,
                        fiber: 9.9    
                    })
}
```


###  2.2. <a name='patientsget'></a>Patients

**Duvidas**: 
* O que é o campo `nutritionist`? Deve conter o nome do nutricionista relacionado ao paciente.
* Qual o mapa numério de `ethnicGroup`? 0 para branco/hispânico e 1.1 para afrodescendentes.
* Qual o mapa numério de `biologicalSex`? Variavel do tipo inteiro, onde 0 = feminino ou 1 = masculino.
* Qual o mapa numério de `physicalActivityLevel`? 

####  2.2.1. <a name='QueriesPatients'></a>Queries:

##### getPatientInfo
Rota:
```
query {
    getPatientInfo(uuidPatient: String!, uuidUser: String!) {
        uuid,
        name,
        ethnicGroup,
        email,
        dateOfBirth,
        nutritionist,
        cpf,
        biologicalSex,
    }
}
```

Exemplo:
```
query {
    getPatientInfo(uuidPatient: "TODO", uuidUser: "ba179e310491460ebaa7260cf355180f") {
        uuid,
        name,
        ethnicGroup,
        email,
        dateOfBirth,
        nutritionist,
        cpf,
        biologicalSex,
    }
}
```

##### getAllPatients

Rota:
```
query {
    getAllPatients(uuidUser: String!, indexPage: Int!, sizePage: Int!) {
        uuid,
        name,
        ethnicGroup,
        email,
        dateOfBirth,
        nutritionist,
        cpf,
        biologicalSex,
    }
}
```

Exemplo:
```
query {
    getAllPatients(uuidUser: "ba179e310491460ebaa7260cf355180f", indexPage: 0, sizePage: 5) {
        uuid,
        name,
        ethnicGroup,
        email,
        dateOfBirth,
        nutritionist,
        cpf,
        biologicalSex,
    }
}
```

##### getPatientRecords
Rota:
```
query {
    getPatientRecords(uuidPatient: String!, uuidUser: String!, indexPage: Int!, sizePage: Int!) {
        uuid,
        uuidPatient,
        corporalMass,
        height,
        abdominal,
        isAthlete,
        age,
        physicalActivityLevel,
        observations,
        subscapular,
        triceps,
        biceps,
        chest,
        axillary,
        supriailiac,
        thigh,
        calf,
        waistCirc,
        abdominalCirc,
        hipsCirc,
        rightArmCirc,
        thighCirc,
        calfCirc,
        muscularMass,
        corporalDensity,
        bodyFat,
        methabolicRate,
        energyRequirements,
    }
}
```

Exemplo:
```
query {
    getPatientRecords(uuidPatient: "TODO", uuidUser: "ba179e310491460ebaa7260cf355180f", indexPage: 0, sizePage: 5) {
        uuid,
        uuidPatient,
        corporalMass,
        height,
        abdominal,
        isAthlete,
        age,
        physicalActivityLevel,
        observations,
        subscapular,
        triceps,
        biceps,
        chest,
        axillary,
        supriailiac,
        thigh,
        calf,
        waistCirc,
        abdominalCirc,
        hipsCirc,
        rightArmCirc,
        thighCirc,
        calfCirc,
        muscularMass,
        corporalDensity,
        bodyFat,
        methabolicRate,
        energyRequirements,
    }
}
```

##### getSingleRecord
Rota:
```
query {
    getSingleRecord(uuidRecord: String!) {
        uuid,
        uuidPatient,
        corporalMass,
        height,
        abdominal,
        isAthlete,
        age,
        physicalActivityLevel,
        observations,
        subscapular,
        triceps,
        biceps,
        chest,
        axillary,
        supriailiac,
        thigh,
        calf,
        waistCirc,
        abdominalCirc,
        hipsCirc,
        rightArmCirc,
        thighCirc,
        calfCirc,
        muscularMass,
        corporalDensity,
        bodyFat,
        methabolicRate,
        energyRequirements,
    }
}
```

Exemplo:
```
query {
    getSingleRecord(uuidRecord: "TODO") {
        uuid,
        uuidPatient,
        corporalMass,
        height,
        abdominal,
        isAthlete,
        age,
        physicalActivityLevel,
        observations,
        subscapular,
        triceps,
        biceps,
        chest,
        axillary,
        supriailiac,
        thigh,
        calf,
        waistCirc,
        abdominalCirc,
        hipsCirc,
        rightArmCirc,
        thighCirc,
        calfCirc,
        muscularMass,
        corporalDensity,
        bodyFat,
        methabolicRate,
        energyRequirements,
    }
}
```

####  2.2.2. <a name='MutationsPatients'></a>Mutation

##### createPatientRecord
Rota:
```
mutation {
    createPatientRecord(uuidUser: String!, 
                        uuidPatient: String!,
                        input: {
                            corporalMass: Float,
                            height: Float,
                            abdominal: Float,
                            isAthlete: Boolean,
                            age: Int,
                            physicalActivityLevel: Float,
                            observations: String,
                            subscapular: Float,
                            triceps: Float,
                            biceps: Float,
                            chest: Float,
                            axillary: Float,
                            supriailiac: Float,
                            thigh: Float,
                            calf: Float,
                            waistCirc: Float,
                            abdominalCirc: Float,
                            hipsCirc:   Float,
                            rightArmCirc: Float,
                            thighCirc:  Float,
                            calfCirc:   Float,
                            muscularMass: Float,
                            corporalDensity: Float,
                            bodyFat:    Float,
                            methabolicRate: Float,
                            energyRequirements: Float,
                        })
}
```

Exemplo:
```
mutation {
    createPatientRecord(uuidUser: "ba179e310491460ebaa7260cf355180f", 
                        uuidPatient: "d0738c5d83994872a71dfbcec704e2e8",
                        input: {
                            corporalMass: 104.0,
                            height: 1.83,
                            abdominal: 110.0,
                            isAthlete: false,
                            age: 38,
                            physicalActivityLevel: 1.0,
                            observations: "Fumante",
                            subscapular: 1.0,
                            triceps: 1.0,
                            biceps: 1.0,
                            chest: 1.0,
                            axillary: 1.0,
                            supriailiac: 1.0,
                            thigh: 1.0,
                            calf: 1.0,
                            waistCirc: 1.0,
                            abdominalCirc: 1.0,
                            hipsCirc:   1.0,
                            rightArmCirc: 1.0,
                            thighCirc:  1.0,
                            calfCirc:   1.0,
                            muscularMass: 1.0,
                            corporalDensity: 1.0,
                            bodyFat:    1.0,
                            methabolicRate: 1.0,
                            energyRequirements: 1.0,
                        })
}
```

##### removePatientRecord
Rota:
```
mutation {
    removePatientRecord(uuidPatientRecord: String!)
}
```

Exemplo:
```
mutation {
    removePatientRecord(uuidPatientRecord: "760668c9a8a949139d8ef7ccb7e23043")
}
```

##### updatePatientRecord
Rota:
```
mutation {
    updatePatientRecord(uuidPatientRecord: String!,
                        input: {
                            corporalMass: Float,
                            height: Float,
                            abdominal: Float,
                            isAthlete: Boolean,
                            age: Int,
                            physicalActivityLevel: Float,
                            observations: String,
                            subscapular: Float,
                            triceps: Float,
                            biceps: Float,
                            chest: Float,
                            axillary: Float,
                            supriailiac: Float,
                            thigh: Float,
                            calf: Float,
                            waistCirc: Float,
                            abdominalCirc: Float,
                            hipsCirc:   Float,
                            rightArmCirc: Float,
                            thighCirc:  Float,
                            calfCirc:   Float,
                            muscularMass: Float,
                            corporalDensity: Float,
                            bodyFat:    Float,
                            methabolicRate: Float,
                            energyRequirements: Float,
                        })
}
```

Exemplo:
```
mutation {
    updatePatientRecord(uuidPatientRecord: "760668c9a8a949139d8ef7ccb7e23043",
                        input: {
                            corporalMass: 104.0,
                            height: 1.83,
                            abdominal: 110.0,
                            isAthlete: false,
                            age: 38,
                            physicalActivityLevel: 1.0,
                            observations: "Fumante",
                            subscapular: 1.0,
                            triceps: 1.0,
                            biceps: 1.0,
                            chest: 1.0,
                            axillary: 1.0,
                            supriailiac: 1.0,
                            thigh: 1.0,
                            calf: 1.0,
                            waistCirc: 1.0,
                            abdominalCirc: 1.0,
                            hipsCirc:   1.0,
                            rightArmCirc: 1.0,
                            thighCirc:  1.0,
                            calfCirc:   1.0,
                            muscularMass: 1.0,
                            corporalDensity: 1.0,
                            bodyFat:    1.0,
                            methabolicRate: 1.0,
                            energyRequirements: 1.0,
                        })
}
```

##### createPatient
Rota:
```
mutation {
    createPatient(  uuidUser: String!, 
                    input: {
                        name:  String,
                        ethnicGroup: Float, // 0 para branco/hispânico e 1.1 para afrodescendentes.
                        email: String,
                        dateOfBirth: String,
                        nutritionist: String, // Deve conter o nome do nutricionista relacionado ao paciente.
                        cpf: String,
                        biologicalSex: Int, // 0 = feminino ou 1 = masculino.
                    })
}
```

Exemplo:
```
mutation {
    createPatient(  uuidUser: "ba179e310491460ebaa7260cf355180f", 
                    input: {
                        name:  "Luís Iago José Lima",
                        ethnicGroup: 0,
                        email: "luisiagojoselima@metalplasma.com.br",
                        dateOfBirth: "21/01/1957",
                        nutritionist: "Ocimar",
                        cpf: "412.281.778-13",
                        biologicalSex: 0,
                    })
}
```

##### removePatient
Rota:
```
mutation {
    removePatient(  uuidPatient: String!,
                    uuidUser: String!)
}
```

Exemplo:
```
mutation {
    removePatient(  uuidPatient: "d0738c5d83994872a71dfbcec704e2e8",
                    uuidUser: "ba179e310491460ebaa7260cf355180f")
}
```

##### updatePatient
Rota:
```
mutation {
    updatePatient(  uuidPatient: String!, 
                    uuidUser: String!, 
                    input: {
                        name:  String,
                        ethnicGroup: Float, // 0 para branco/hispânico e 1.1 para afrodescendentes.
                        email: String,
                        dateOfBirth: String,
                        nutritionist: String, // Deve conter o nome do nutricionista relacionado ao paciente.
                        cpf: String,
                        biologicalSex: Int, // 0 = feminino ou 1 = masculino.
                    })
}
```

Exemplo:
```
mutation {
    updatePatient(  uuidPatient: "d0738c5d83994872a71dfbcec704e2e8", 
                    uuidUser: "ba179e310491460ebaa7260cf355180f", 
                    input: {
                        name:  "Francisco Carlos Eduardo Alves",
                        ethnicGroup: 1.1,
                        email: "franciscoalves@imobideal.com",
                        dateOfBirth: "08/02/1954",
                        nutritionist: "Ocimar",
                        cpf: "051.223.837-58",
                        biologicalSex: 0,
                    })
}
```
