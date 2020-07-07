
# Rotas

<!-- vscode-markdown-toc -->
* [REST](#REST)
* [GraphQL](#GraphQL)
	* [/food/graphql](#foodgraphql)
		* [Queries:](#Queries:)
		* [Mutations:](#Mutations:)
	* [/patients/get](#patientsget)
		* [Queries:](#Queries:-1)
		* [Mutation](#Mutation)

<!-- vscode-markdown-toc-config
	numbering=true
	autoSave=true
	/vscode-markdown-toc-config -->
<!-- /vscode-markdown-toc -->



##  1. <a name='REST'></a>REST

##  2. <a name='GraphQL'></a>GraphQL

*Bizu*: Os campos com exclamação `!` são obrigatórios, os demais podem ser omitidos se não forem relevantes para a _query_;

###  2.1. <a name='foodgraphql'></a>/food/graphql

####  2.1.1. <a name='Queries:'></a>Queries:

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


####  2.1.2. <a name='Mutations:'></a>Mutations:

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


###  2.2. <a name='patientsget'></a>/patients/get

**Duvidas**: 
* O que é o campo `nutritionist`?
* Qual o mapa numério de `ethnicGroup`?
* Qual o mapa numério de `biologicalSex`?
* Qual o mapa numério de `physicalActivityLevel`?

####  2.2.1. <a name='Queries:-1'></a>Queries:

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

####  2.2.2. <a name='Mutation'></a>Mutation

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
                        uuidPatient: "TODO",
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
    removePatientRecord(uuidRecord: String!)
}
```

Exemplo:
```
mutation {
    removePatientRecord(uuidRecord: "TODO")
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
    updatePatientRecord(uuidPatientRecord: "TODO",
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
                        ethnicGroup: Float,
                        email: String,
                        dateOfBirth: String,
                        nutritionist: String,
                        cpf: String,
                        biologicalSex: Int,
                    })
}
```

Exemplo:
```
mutation {
    createPatient(  uuidUser: "ba179e310491460ebaa7260cf355180f", 
                    input: {
                        name:  "Francisco Carlos Eduardo Alves",
                        ethnicGroup: 0,
                        email: "franciscoalves@imobideal.com",
                        dateOfBirth: "08/02/1954",
                        nutritionist: "",
                        cpf: "051.223.837-58",
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
    removePatient(  uuidPatient: "TODO",
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
                        ethnicGroup: Float,
                        email: String,
                        dateOfBirth: String,
                        nutritionist: String,
                        cpf: String,
                        biologicalSex: Int,
                    })
}
```

Exemplo:
```
mutation {
    updatePatient(  uuidPatient: "TODO", 
                    uuidUser: "ba179e310491460ebaa7260cf355180f", 
                    input: {
                        name:  "Francisco Carlos Eduardo Alves",
                        ethnicGroup: 0,
                        email: "franciscoalves@imobideal.com",
                        dateOfBirth: "08/02/1954",
                        nutritionist: "",
                        cpf: "051.223.837-58",
                        biologicalSex: 0,
                    })
}
```
