# Veteriner Yönetim Sistemi API Dokümantasyonu
Bu API, bir veteriner kliniğinin kendi işlerini yönetebilmesi için kullanılır. API, veteriner doktorları, müşterileri, hayvanları, aşıları ve randevuları yönetmek için çeşitli endpoint'ler sağlar.

* API kök URL'i: http://localhost:8080
* Bu projenin dokümantasyonunda Postman ve json üzerinden sorgular kullanılmıştır



# Doctor Entity Operasyonları
## Doctor Bilgisi Getirme
#### Endpoint: GET http://localhost:8080/doctor/{id}
#### Description: ID kullanarak belirli bir doktorun bilgilerini getirir.
#### Path Variables:
doctorId (Long) - Doktorun kimlik numarası.
#### Request:
Örnek: http://localhost:8080/doctor/1
####  Response:
HTTP Status: 200 OK
####  Body:

```
  {
    "id": 1,
    "name": "Dr. Smith",
    "phone": "1234567890",
    "mail": "drsmith@example.com",
    "address": "789 Maple Ave",
    "city": "Springfield"
}
```

HTTP Status: 404 Not Found (Eğer doktor bulunamazsa)
## Yeni Doktor Ekleme
#### Endpoint: POST http://localhost:8080/doctor/save
#### Description: Yeni bir doktor ekler.
#### Request:
####  Body:
   ```
   {
   "name": "Dr. Jane Smith",
   "phone": "+9876543210",
   "mail": "jane.smith@example.com",
   "address": "456 Oak St",
   "city": "New City"
   }
   ```
####   Response:
HTTP Status: 201 Created
####  Body:

```
   {
   "id": 2,
   "name": "Dr. Jane Smith",
   "phone": "+9876543210",
   "mail": "jane.smith@example.com",
   "address": "456 Oak St",
   "city": "New City"
   }
   ```
## Doktor Bilgisi Güncelleme
####  Endpoint: PUT http://localhost:8080/doctor/save
#### Description: Belirli bir doktorun bilgilerini günceller.
#### Path Variables:
doctorId (Long) - Güncellenecek doktorun kimlik numarası verilmeli.
#### Request:
####  Body:
   ```
   {
   "doctorId" : 2
   "name": "Dr. Jane Doe",
   "phone": "+1234567890",
   "mail": "jane.doe@example.com",
   "address": "789 Pine St",
   "city": "Another City"
   }
   ```
####  Response:
HTTP Status: 200 OK
####  Body:


```
   {
   "id": 2,
   "name": "Dr. Jane Doe",
   "phone": "+1234567890",
   "mail": "jane.doe@example.com",
   "address": "789 Pine St",
   "city": "Another City"
 
   }
   ```
HTTP Status: 404 Not Found (Eğer doktor bulunamazsa)

# Animal Entity Operasyonları
## Hayvan Bilgisi Getirme
####  Endpoint: GET http://localhost:8080/animal/{id}
####  Description: Belirli bir hayvanın bilgilerini getirir.
####  Path Variables:
animalId (Long) - Hayvanın kimlik numarası.
####  Request:
Örnek: http://localhost:8080/animal/1
####  Response:
HTTP Status: 200 OK
####  Body:

```
   {
    "id": 1,
    "name": "Luna",
    "species": "Dog",
    "breed": "Golden Retriever",
    "gender": "Female",
    "colour": "Golden",
    "dateOfBirth": "2019-06-12",
    "customer": {
        "id": 3,
        "name": "Olivia Garcia",
        "phone": "9876543210",
        "mail": "olivia@example.com",
        "address": "963 Oak St",
        "city": "Springfield"
    }
}
   ```
HTTP Status: 404 Not Found (Eğer hayvan bulunamazsa)
## Yeni Hayvan Ekleme
#### Endpoint: POST http://localhost:8080/animal/save
#### Description: Yeni bir hayvan ekler.
####  Request:
#### Body:
   ```
    {
        "name": "Buddy",
        "species": "Dog",
        "breed": "Labrador Retriever",
        "gender": "Male",
        "colour": "Chocolate",
        "dateOfBirth": "2017-04-19",
        "customer": {
            "id": 4
        }
    }
   ```
#### Response:
HTTP Status: 201 Created
####  Body:
  ```
   {
    "id": 5,
    "name": "Buddy",
    "species": "Dog",
    "breed": "Labrador Retriever",
    "gender": "Male",
    "colour": "Chocolate",
    "dateOfBirth": "2017-04-19",
    "customer": {
        "id": 4,
        "name": "Liam Martinez",
        "phone": "3335557777",
        "mail": "liam@example.com",
        "address": "741 Elm St",
        "city": "Hill Valley"
    }
}
   ```
## Hayvan Bilgisi Güncelleme
#### Endpoint: PUT http://localhost:8080/animal/save
#### Description: Belirli bir hayvanın bilgilerini günceller.
####  Path Variables:
animalId (Long) - Güncellenecek hayvanın kimlik numarası gerekli.
#### Request:
####  Body:

```
   {
        "id" : 5,
        "name": "BuddyGüncel",
        "species": "Dog",
        "breed": "Labrador Retriever",
        "gender": "Male",
        "colour": "Chocolate",
        "dateOfBirth": "2017-04-19",
        "customer": {
            "id": 4
        }
    }
   ```
#### Response:
HTTP Status: 200 OK
####  Body:

```
   {
    "id": 5,
    "name": "BuddyGüncel",
    "species": "Dog",
    "breed": "Labrador Retriever",
    "gender": "Male",
    "colour": "Chocolate",
    "dateOfBirth": "2017-04-19",
    "customer": {
        "id": 4,
        "name": "Liam Martinez",
        "phone": "3335557777",
        "mail": "liam@example.com",
        "address": "741 Elm St",
        "city": "Hill Valley"
    }
}
   ```
HTTP Status: 404 Not Found (Eğer hayvan bulunamazsa)

## Tüm Hayvanları listeleme
#### Endpoint: http://localhost:8080/animal/find-all
#### Description: Tüm hayvanları listeler.

#### Response:

####  Body:
```
[
    {
        "id": 1,
        "name": "Luna",
        "species": "Dog",
        "breed": "Golden Retriever",
        "gender": "Female",
        "colour": "Golden",
        "dateOfBirth": "2019-06-12",
        "customer": {
            "id": 3,
            "name": "Olivia Garcia",
            "phone": "9876543210",
            "mail": "olivia@example.com",
            "address": "963 Oak St",
            "city": "Springfield"
        }
    },
    {
        "id": 2,
        "name": "Simba",
        "species": "Cat",
        "breed": "Siamese",
        "gender": "Male",
        "colour": "Beige",
        "dateOfBirth": "2020-03-25",
        "customer": {
            "id": 2,
            "name": "Noah Wilson",
            "phone": "5554446666",
            "mail": "noah@example.com",
            "address": "852 Maple St",
            "city": "Rivertown"
        }
    },
    {
        "id": 3,
        "name": "Rocky",
        "species": "Dog",
        "breed": "German Shepherd",
        "gender": "Male",
        "colour": "Black/Brown",
        "dateOfBirth": "2018-11-30",
        "customer": {
            "id": 2,
            "name": "Noah Wilson",
            "phone": "5554446666",
            "mail": "noah@example.com",
            "address": "852 Maple St",
            "city": "Rivertown"
        }
    },
    {
        "id": 4,
        "name": "Whiskers",
        "species": "Cat",
        "breed": "Persian",
        "gender": "Female",
        "colour": "White",
        "dateOfBirth": "2020-08-10",
        "customer": {
            "id": 1,
            "name": "Emma Johnson",
            "phone": "1112223334",
            "mail": "emma@example.com",
            "address": "789 Cedar St",
            "city": "Metropolis"
        }
    },
    {
        "id": 5,
        "name": "BuddyGüncel",
        "species": "Dog",
        "breed": "Labrador Retriever",
        "gender": "Male",
        "colour": "Chocolate",
        "dateOfBirth": "2017-04-19",
        "customer": {
            "id": 4,
            "name": "Liam Martinez",
            "phone": "3335557777",
            "mail": "liam@example.com",
            "address": "741 Elm St",
            "city": "Hill Valley"
        }
    }
]
```


## İsme Göre Hayvan Filtreleme
#### Endpoint: http://localhost:8080/animal/name/{name}
#### Description: O isime sahip hayvanları listeler. Aynı isimden birden çok olabileceği için hepsini listelemeyi seçtim. Aynı zamanda ignorecase kullanıyor.
#### Path Variables:
name (String) - Hayvanın adı.

#### Request:
#### Örnek: http://localhost:8080/animal/name/Simba
#### Response:
HTTP Status: 200 OKs
#### Body:
```
[
    {
        "id": 2,
        "name": "Simba",
        "species": "Cat",
        "breed": "Siamese",
        "gender": "Male",
        "colour": "Beige",
        "dateOfBirth": "2020-03-25",
        "customer": {
            "id": 2,
            "name": "Noah Wilson",
            "phone": "5554446666",
            "mail": "noah@example.com",
            "address": "852 Maple St",
            "city": "Rivertown"
        }
    }
]
```
## Belirli Bir Hayvanı Silme
####  Endpoint: DELETE http://localhost:8080/animal/delete/{id}
####  Description: Belirli bir hayvanı siler.
####  Path Variables:
id (Long) - Hayvanın kimlik numarası.
####  Request:
####  Örnek: http://localhost:8080/animal/delete/1
#### Response:
HTTP Status: 200 OK
#### Body:
   ```
   "1 numaralı hayvan silindi."
```
# Appointment Entity Operasyonları
## Yeni Randevu Oluşturma
#### Endpoint: POST http://localhost:8080/appointment/save
####  Description: Yeni bir randevu oluşturur.
####  Request:
Body:
   ```
   {
    "appointmentDate": "2023-12-25T08:00:00",
    "animal": {
        "id": 1
    },
    "doctor": {
        "id": 1
    }
}

   ```
#### Response:
HTTP Status: 201 Created
####  Body:
   ```
   {
    "id": 1,
    "appointmentDate": "2023-12-25T08:00:00",
    "doctor": {
        "id": 1,
        "name": "Dr. Smith",
        "phone": "1234567890",
        "mail": "drsmith@example.com",
        "address": "789 Maple Ave",
        "city": "Springfield"
    },
    "animal": {
        "id": 1,
        "name": "Luna",
        "species": "Dog",
        "breed": "Golden Retriever",
        "gender": "Female",
        "colour": "Golden",
        "dateOfBirth": "2019-06-12",
        "customer": {
            "id": 3,
            "name": "Olivia Garcia",
            "phone": "9876543210",
            "mail": "olivia@example.com",
            "address": "963 Oak St",
            "city": "Springfield"
        }
    }
}
   ```
## Randevu Bilgisi Güncelleme
####  Endpoint: PUT http://localhost:8080/appointment/update
####  Description: Belirli bir randevunun bilgilerini günceller.
####  Request:
####  Body:
   ```
  {
    "id": 1
    "appointmentDate": "2023-12-25T08:00:00",
    "animal": {
        "id": 3
    },
    "doctor": {
        "id": 1
    }
}
   ```
####   Response:
HTTP Status: 200 OK
####  Body:
   ```
  {
    "id": 1,
    "appointmentDate": "2023-12-25T08:00:00",
    "doctor": {
        "id": 1,
        "name": "Dr. Smith",
        "phone": "1234567890",
        "mail": "drsmith@example.com",
        "address": "789 Maple Ave",
        "city": "Springfield"
    },
    "animal": {
        "id": 1,
        "name": "Rocky",
        "species": "Dog",
        "breed": "German Shepherd",
        "gender": "Male",
        "colour": "Black/Brown",
        "dateOfBirth": "2018-11-30",
        "customer": {
            "id": 2,
            "name": "Noah Wilson",
            "phone": "5554446666",
            "mail": "noah@example.com",
            "address": "852 Maple St",
            "city": "Rivertown"
        }
    }
}
   ```
HTTP Status: 404 Not Found (Eğer randevu bulunamazsa)
## Belirli Bir Randevu Bilgisini Getirme
####  Endpoint: GET http://localhost:8080/appointment/{appointmentId}
####  Description: Belirli bir randevunun bilgilerini getirir.
####  Path Variables:
appointmentId (Long) - Randevunun kimlik numarası.
Request:
####  Örnek: http://localhost:8080/appointment/1
####  Response:
HTTP Status: 200 OK
####  Body:
   ```
   {
    "id": 1,
    "appointmentDate": "2023-12-25T08:00:00",
    "doctor": {
        "id": 1,
        "name": "Dr. Smith",
        "phone": "1234567890",
        "mail": "drsmith@example.com",
        "address": "789 Maple Ave",
        "city": "Springfield"
    },
    "animal": {
        "id": 1,
        "name": "Luna",
        "species": "Dog",
        "breed": "Golden Retriever",
        "gender": "Female",
        "colour": "Golden",
        "dateOfBirth": "2019-06-12",
        "customer": {
            "id": 3,
            "name": "Olivia Garcia",
            "phone": "9876543210",
            "mail": "olivia@example.com",
            "address": "963 Oak St",
            "city": "Springfield"
        }
    }
}
   ```
HTTP Status: 404 Not Found (Eğer randevu bulunamazsa)
## Belirli Tarih Aralığına ve Doktora Göre Randevu Arama
####  Endpoint: GET http://localhost:8080/appointment/findByDateAndDoctor
####  Description: Belirli bir tarih aralığına ve doktora göre randevuları getirir.
####  Query Parameters:
startDate (String) - Arama başlangıç tarihi (ISO formatında).
endDate (String) - Arama bitiş tarihi (ISO formatında).
doctorId (Long) - Doktorun kimlik numarası.
####  Request:
#### Örnek: http://localhost:8080/appointment/findByDateAndDoctor?startDate=2023-12-25&endDate=2023-12-30&id=4
####  Response:
HTTP Status: 200 OK
####  Body:
   ```
[
    {
        "id": 2,
        "appointmentDate": "2023-12-30T11:00:00",
        "doctor": {
            "id": 4,
            "name": "Dr. Lee",
            "phone": "7778889999",
            "mail": "drlee@example.com",
            "address": "222 Birch Rd",
            "city": "Los Angeles"
        },
        "animal": {
            "id": 2,
            "name": "Simba",
            "species": "Cat",
            "breed": "Siamese",
            "gender": "Male",
            "colour": "Beige",
            "dateOfBirth": "2020-03-25",
            "customer": {
                "id": 2,
                "name": "Noah Wilson",
                "phone": "5554446666",
                "mail": "noah@example.com",
                "address": "852 Maple St",
                "city": "Rivertown"
            }
        }
    },
    {
        "id": 3,
        "appointmentDate": "2023-12-30T15:00:00",
        "doctor": {
            "id": 4,
            "name": "Dr. Lee",
            "phone": "7778889999",
            "mail": "drlee@example.com",
            "address": "222 Birch Rd",
            "city": "Los Angeles"
        },
        "animal": {
            "id": 5,
            "name": "BuddyGüncel",
            "species": "Dog",
            "breed": "Labrador Retriever",
            "gender": "Male",
            "colour": "Chocolate",
            "dateOfBirth": "2017-04-19",
            "customer": {
                "id": 4,
                "name": "Liam Martinez",
                "phone": "3335557777",
                "mail": "liam@example.com",
                "address": "741 Elm St",
                "city": "Hill Valley"
            }
        }
    }
]
   ```
## Tüm Randevuları Getirme
####  Endpoint: GET http://localhost:8080/appointment/find-all
####  Description: Tüm randevuları getirir.
#### Request:
####  Örnek: http://localhost:8080/appointment/find-all
####  Response:
HTTP Status: 200 OK
####  Body:
   ```
   [
    {
        "id": 1,
        "appointmentDate": "2023-12-25T08:00:00",
        "doctor": {
            "id": 1,
            "name": "Dr. Smith",
            "phone": "1234567890",
            "mail": "drsmith@example.com",
            "address": "789 Maple Ave",
            "city": "Springfield"
        },
        "animal": {
            "id": 1,
            "name": "Luna",
            "species": "Dog",
            "breed": "Golden Retriever",
            "gender": "Female",
            "colour": "Golden",
            "dateOfBirth": "2019-06-12",
            "customer": {
                "id": 3,
                "name": "Olivia Garcia",
                "phone": "9876543210",
                "mail": "olivia@example.com",
                "address": "963 Oak St",
                "city": "Springfield"
            }
        }
    },
    {
        "id": 2,
        "appointmentDate": "2023-12-30T11:00:00",
        "doctor": {
            "id": 4,
            "name": "Dr. Lee",
            "phone": "7778889999",
            "mail": "drlee@example.com",
            "address": "222 Birch Rd",
            "city": "Los Angeles"
        },
        "animal": {
            "id": 2,
            "name": "Simba",
            "species": "Cat",
            "breed": "Siamese",
            "gender": "Male",
            "colour": "Beige",
            "dateOfBirth": "2020-03-25",
            "customer": {
                "id": 2,
                "name": "Noah Wilson",
                "phone": "5554446666",
                "mail": "noah@example.com",
                "address": "852 Maple St",
                "city": "Rivertown"
            }
        }
    },
    {
        "id": 3,
        "appointmentDate": "2023-12-30T15:00:00",
        "doctor": {
            "id": 4,
            "name": "Dr. Lee",
            "phone": "7778889999",
            "mail": "drlee@example.com",
            "address": "222 Birch Rd",
            "city": "Los Angeles"
        },
        "animal": {
            "id": 5,
            "name": "BuddyGüncel",
            "species": "Dog",
            "breed": "Labrador Retriever",
            "gender": "Male",
            "colour": "Chocolate",
            "dateOfBirth": "2017-04-19",
            "customer": {
                "id": 4,
                "name": "Liam Martinez",
                "phone": "3335557777",
                "mail": "liam@example.com",
                "address": "741 Elm St",
                "city": "Hill Valley"
            }
        }
    },
    {
        "id": 4,
        "appointmentDate": "2023-12-27T12:00:00",
        "doctor": {
            "id": 2,
            "name": "Dr. Johnson",
            "phone": "9876543210",
            "mail": "drjohnson@example.com",
            "address": "456 Pine St",
            "city": "Oakland"
        },
        "animal": {
            "id": 5,
            "name": "BuddyGüncel",
            "species": "Dog",
            "breed": "Labrador Retriever",
            "gender": "Male",
            "colour": "Chocolate",
            "dateOfBirth": "2017-04-19",
            "customer": {
                "id": 4,
                "name": "Liam Martinez",
                "phone": "3335557777",
                "mail": "liam@example.com",
                "address": "741 Elm St",
                "city": "Hill Valley"
            }
        }
    },
    {
        "id": 5,
        "appointmentDate": "2023-12-25T17:00:00",
        "doctor": {
            "id": 1,
            "name": "Dr. Smith",
            "phone": "1234567890",
            "mail": "drsmith@example.com",
            "address": "789 Maple Ave",
            "city": "Springfield"
        },
        "animal": {
            "id": 1,
            "name": "Luna",
            "species": "Dog",
            "breed": "Golden Retriever",
            "gender": "Female",
            "colour": "Golden",
            "dateOfBirth": "2019-06-12",
            "customer": {
                "id": 3,
                "name": "Olivia Garcia",
                "phone": "9876543210",
                "mail": "olivia@example.com",
                "address": "963 Oak St",
                "city": "Springfield"
            }
        }
    }
]
   ```
## Belirli Tarih Aralığına ve Hayvana Göre Randevu Arama
####  Endpoint: GET http://localhost:8080/appointment/findByDateAndAnimal
#### Description: Belirli bir tarih aralığına ve hayvana göre randevuları getirir.
####  Query Parameters:
startDate (String) - Arama başlangıç tarihi (ISO formatında).
endDate (String) - Arama bitiş tarihi (ISO formatında).
animalId (Long) - Hayvanın kimlik numarası.
####  Request:
####  Örnek: http://localhost:8080/appointment/findByDateAndAnimal?startDate=2023-12-25&endDate=2024-01-15&id=5
####  Response:
HTTP Status: 200 OK
####  Body:
   ```
   [
    {
        "id": 3,
        "appointmentDate": "2023-12-30T15:00:00",
        "doctor": {
            "id": 4,
            "name": "Dr. Lee",
            "phone": "7778889999",
            "mail": "drlee@example.com",
            "address": "222 Birch Rd",
            "city": "Los Angeles"
        },
        "animal": {
            "id": 5,
            "name": "BuddyGüncel",
            "species": "Dog",
            "breed": "Labrador Retriever",
            "gender": "Male",
            "colour": "Chocolate",
            "dateOfBirth": "2017-04-19",
            "customer": {
                "id": 4,
                "name": "Liam Martinez",
                "phone": "3335557777",
                "mail": "liam@example.com",
                "address": "741 Elm St",
                "city": "Hill Valley"
            }
        }
    },
    {
        "id": 4,
        "appointmentDate": "2023-12-27T12:00:00",
        "doctor": {
            "id": 2,
            "name": "Dr. Johnson",
            "phone": "9876543210",
            "mail": "drjohnson@example.com",
            "address": "456 Pine St",
            "city": "Oakland"
        },
        "animal": {
            "id": 5,
            "name": "BuddyGüncel",
            "species": "Dog",
            "breed": "Labrador Retriever",
            "gender": "Male",
            "colour": "Chocolate",
            "dateOfBirth": "2017-04-19",
            "customer": {
                "id": 4,
                "name": "Liam Martinez",
                "phone": "3335557777",
                "mail": "liam@example.com",
                "address": "741 Elm St",
                "city": "Hill Valley"
            }
        }
    }
]
```

# AvailableDate Entity Operasyonları
## Doktorun Müsait Günlerini Getirme
#### Endpoint: GET http://localhost:8080/available-dates/{id}
#### Description: Belirli bir doktorun müsait günlerini getirir.
#### Path Variables:
doctorId (Long) - Doktorun kimlik numarası.
#### Request:
####  Örnek: http://localhost:8080/available-dates/3
####  Response:
HTTP Status: 200 OK
####  Body:
   ```
[
    {
        "id": 3,
        "availableDate": "2023-12-28",
        "doctor": {
            "id": 3,
            "name": "Dr. Garcia",
            "phone": "3332221111",
            "mail": "drgarcia@example.com",
            "address": "111 Cedar Blvd",
            "city": "San Francisco"
        }
    }
]
   ```
## Yeni Müsait Gün Ekleme
####  Endpoint: POST http://localhost:8080/available-date/save
#### Description: Belirli bir doktora yeni bir müsait gün ekler.
####  Request:
#### Body:
   ```
   {
    "availableDate": "2023-12-25",
    "doctor": {
        "id": 1
    }
}

   ```
####  Response:
HTTP Status: 200 OK
####  Body:
   ```
   {
    "id": 1,
    "availableDate": "2023-12-25",
    "doctor": {
        "id": 1,
        "name": "Dr. Smith",
        "phone": "1234567890",
        "mail": "drsmith@example.com",
        "address": "789 Maple Ave",
        "city": "Springfield"
    }
}
   ```
HTTP Status: 400 Bad Request (Eğer belirtilen doktor bulunamazsa veya aynı tarih için kayıt mevcutsa)

# Customer Entity Operasyonları
## Müşteri Bilgilerini Getirme
####  Endpoint: GET http://localhost:8080/customer/{id}
####  Description: Belirli bir müşterinin bilgilerini getirir.
####   Path Variables:
id (Long) - Müşterinin kimlik numarası.
####  Request:
####   Örnek: http://localhost:8080/customer/4
####   Response:
HTTP Status: 200 OK
####   Body:
   ```
   {
    "id": 4,
    "name": "Liam Martinez",
    "phone": "3335557777",
    "mail": "liam@example.com",
    "address": "741 Elm St",
    "city": "Hill Valley"
}
   ```
## Yeni Müşteri Ekleme
####   Endpoint: POST http://localhost:8080/customer/save
####  Description: Yeni bir müşteri ekler.
####   Request:
####   Body:
   ```
   {
    "name": "Sophia Johnson",
    "phone": "551244887766",
    "mail": "sophia@example.com",
    "address": "328 Oak Lane",
    "city": "Riverside"
}

   ```
####   Response:
HTTP Status: 200 OK
####    Body:
   ```
   {
    "id": 6,
    "name": "Sophia Johnson",
    "phone": "551244887766",
    "mail": "sophia@example.com",
    "address": "328 Oak Lane",
    "city": "Riverside"
}
   ```
## Tüm Müşterileri Listeleme
####   Endpoint: GET http://localhost:8080/customer/find-all
####    Description: Sistemde kayıtlı tüm müşterileri getirir.
####    Request:
####   Örnek: http://localhost:8080/customer/find-all
####  Response:
HTTP Status: 200 OK
####   Body:
   ```
   [
    {
        "id": 1,
        "name": "Emma Johnson",
        "phone": "1112223334",
        "mail": "emma@example.com",
        "address": "789 Cedar St",
        "city": "Metropolis"
    },
    {
        "id": 2,
        "name": "Noah Wilson",
        "phone": "5554446666",
        "mail": "noah@example.com",
        "address": "852 Maple St",
        "city": "Rivertown"
    },
    {
        "id": 3,
        "name": "Olivia Garcia",
        "phone": "9876543210",
        "mail": "olivia@example.com",
        "address": "963 Oak St",
        "city": "Springfield"
    },
    {
        "id": 4,
        "name": "Liam Martinez",
        "phone": "3335557777",
        "mail": "liam@example.com",
        "address": "741 Elm St",
        "city": "Hill Valley"
    },
    {
        "id": 5,
        "name": "Sophia Davis",
        "phone": "8887779999",
        "mail": "sophia@example.com",
        "address": "369 Birch St",
        "city": "Pleasantville"
    },
    {
        "id": 6,
        "name": "Sophia Johnson",
        "phone": "551244887766",
        "mail": "sophia@example.com",
        "address": "328 Oak Lane",
        "city": "Riverside"
    }
]
   ```
## Müşteriye Ait Hayvanları Getirme
####   Endpoint: GET http://localhost:8080/customer/{customerId}/animals
####   Description: Belirli bir müşteriye ait hayvanları getirir.
####   Path Variables:
customerId (Long) - Müşterinin kimlik numarası.
####  Request:
####  Örnek: http://localhost:8080/customer/1/animals
####   Response:
HTTP Status: 200 OK
####  Body:
   ```
[
    {
        "id": 4,
        "name": "Whiskers",
        "species": "Cat",
        "breed": "Persian",
        "gender": "Female",
        "colour": "White",
        "dateOfBirth": "2020-08-10",
        "customer": {
            "id": 1,
            "name": "Emma Johnson",
            "phone": "1112223334",
            "mail": "emma@example.com",
            "address": "789 Cedar St",
            "city": "Metropolis"
        }
    }
]
   ```
## İsimle Müşteri Arama
####   Endpoint: GET http://localhost:8080/customer/name/{name}
####  Description: Belirli bir isimle müşteri arar.
####   Path Variables:
name (String) - Müşterinin adı.
####   Request:
####   Örnek: http://localhost:8080/customer/name/Emma%20Johnson
####   Response:
HTTP Status: 200 OK
####   Body:
   ```
[
    {
        "id": 1,
        "name": "Emma Johnson",
        "phone": "1112223334",
        "mail": "emma@example.com",
        "address": "789 Cedar St",
        "city": "Metropolis"
    }
]
```

# Vaccine Entity Operasyonları
## Aşı Oluşturma
#### Endpoint: POST http://localhost:8080/vaccine/save
####  Description: Belirli bir hayvana aşı ekler.
####  Request:
####   Body:
   ```
   {
    "name": "Rabies Vaccine",
    "code": "RV001",
    "protectionStartDate": "2023-12-01",
    "protectionFinishDate": "2024-12-01",
    "animal": {
        "id": 1
    }
}

   ```
####   Response:
HTTP Status: 201 Created
####   Body:
   ```
   {
    "id": 1,
    "name": "Rabies Vaccine",
    "code": "RV001",
    "protectionStartDate": "2023-12-01",
    "protectionFinishDate": "2024-12-01",
    "animal": {
        "id": 1,
        "name": "Luna",
        "species": "Dog",
        "breed": "Golden Retriever",
        "gender": "Female",
        "colour": "Golden",
        "dateOfBirth": "2019-06-12",
        "customer": {
            "id": 3,
            "name": "Olivia Garcia",
            "phone": "9876543210",
            "mail": "olivia@example.com",
            "address": "963 Oak St",
            "city": "Springfield"
        }
    },
    "protectionExpired": false
}
   ```
## Hayvana Ait Aşı Bilgilerini Getirme
####   Endpoint: GET http://localhost:8080/vaccine/{animalId}
####   Description: Belirli bir hayvana ait aşı bilgilerini getirir.
####  Path Variables:
animalId (Long) - Hayvanın kimlik numarası.
####   Request:
####   Örnek: http://localhost:8080/vaccine/5
####   Response:
HTTP Status: 200 OK
####   Body:
   ```
[
    {
        "id": 5,
        "name": "Avian Polyomavirus Vaccine",
        "code": "APV001",
        "protectionStartDate": "2023-12-10",
        "protectionFinishDate": "2024-12-10",
        "animal": {
            "id": 5,
            "name": "BuddyGüncel",
            "species": "Dog",
            "breed": "Labrador Retriever",
            "gender": "Male",
            "colour": "Chocolate",
            "dateOfBirth": "2017-04-19",
            "customer": {
                "id": 4,
                "name": "Liam Martinez",
                "phone": "3335557777",
                "mail": "liam@example.com",
                "address": "741 Elm St",
                "city": "Hill Valley"
            }
        }
    }
]
   ```
## Belirli Tarih Aralığında Aşıları Sona Eren Hayvanları Getirme
####  Endpoint: GET http://localhost:8080/vaccine/expiring?startDate=2023-01-01&endDate=2023-12-31
####  Description: Belirli bir tarih aralığında aşıları sona eren hayvanları getirir.
####   Request:
####   Örnek: http://localhost:8080/vaccine/expiring?startDate=2023-10-20&endDate=2024-11-21
####  Response:
HTTP Status: 200 OK
####  Body:
   ```
[
    {
        "id": 2,
        "name": "Simba",
        "species": "Cat",
        "breed": "Siamese",
        "gender": "Male",
        "colour": "Beige",
        "dateOfBirth": "2020-03-25",
        "customer": {
            "id": 2,
            "name": "Noah Wilson",
            "phone": "5554446666",
            "mail": "noah@example.com",
            "address": "852 Maple St",
            "city": "Rivertown"
        }
    },
    {
        "id": 4,
        "name": "Whiskers",
        "species": "Cat",
        "breed": "Persian",
        "gender": "Female",
        "colour": "White",
        "dateOfBirth": "2020-08-10",
        "customer": {
            "id": 1,
            "name": "Emma Johnson",
            "phone": "1112223334",
            "mail": "emma@example.com",
            "address": "789 Cedar St",
            "city": "Metropolis"
        }
    }
]
```