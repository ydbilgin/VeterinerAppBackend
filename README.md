# Veteriner Yönetim Sistemi

Bu proje, bir veteriner kliniğinin kendi işlerini yönetebilmesi için geliştirilmiş bir API'yi içermektedir.

## Başlangıç

Proje, Spring Boot kullanılarak geliştirilmiştir ve PostgreSQL veritabanı kullanmaktadır. Projenin çalıştırılabilmesi için gerekli adımları takip etmek için aşağıdaki talimatları izleyin.

### Önkoşullar

- Java 17 veya üstü
- Maven
- PostgreSQL veritabanı

### Kurulum

1. Bu depoyu klonlayın:

```bash
git clone 
```

2. Veritabanı bağlantı ayarlarını src/main/resources/application.properties dosyasında güncelleyin.
```
spring.datasource.url=jdbc:postgresql://localhost:5432/veteriner
spring.datasource.username=your_username
spring.datasource.password=your_password
```
3. Uygulama başladıktan sonra tarayıcınızdan http://localhost:8080/ adresine giderek ve istenen endpointleri yazarak API dokümantasyonunu görüntüleyebilirsiniz.



## API Temel Özellikleri

### Projede Bulunan Entity'ler
Animal
Customer
Vaccine
Doctor
AvailableDate
Appointment

### Hayvanların ve Sahiplerinin Yönetimi

- Hayvanların sisteme kaydedilmesi, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi.
- Sahiplerin sisteme kaydedilmesi, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi.
- Sahiplerin ismine göre filtrelenmesi.
- Hayvanların ismine göre filtrelenmesi.
- Bir sahibin tüm kayıtlı hayvanlarının görüntülenmesi.
### Aşı Yönetimi

- Hayvanlara yapılan aşıların kaydedilmesi, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi.
- Belirli bir hayvana ait tüm aşı kayıtlarının hayvan ID'sine göre listelenmesi.
- Kullanıcıların aşı koruyuculuk bitiş tarihi yaklaşan hayvanları listeleyebilmesi.
### Randevu Yönetimi

- Hayvanların aşı ve muayene randevularının oluşturulması, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi.
- Randevuların tarih ve saat bilgilerinin kaydedilmesi.
- Randevu oluşturulurken doktorun uygun gün ve saatlerinin kontrol edilmesi.
- Randevuların kullanıcı tarafından belirtilen tarih aralığına ve doktora göre filtrelenmesi.
- Randevuların kullanıcı tarafından belirtilen tarih aralığına ve hayvana göre filtrelenmesi.
### Veteriner Doktor Yönetimi

- Veteriner doktorların kaydedilmesi, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi.
### Doktorların Müsait Günlerinin Yönetimi
- Doktorların uygun günlerinin eklenmesi, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi.