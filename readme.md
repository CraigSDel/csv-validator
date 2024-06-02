# CSV Data Validator 📊

This Java project provides a dynamic CSV data validation tool. It allows you to validate CSV files against custom rules and ensure data integrity. Whether you're dealing with financial records, user data, or any other structured data in CSV format, this tool has got you covered! 🚀

## Features

- **Dynamic Rules**: Define your own validation rules based on column names, data types, and specific conditions.
- **Efficient Processing**: Utilizes efficient algorithms to process large CSV files without consuming excessive memory.

## Getting Started

1. Clone this repository:

    ```bash
        git clone https://github.com/CraigSDel/csv-validator.git
    ```

2. Build the project:

    ```bash
        cd csv-data-validator
        mvn clean install
    ```

3. Create a list of validators to validate the data in a CSV:
   ```java
        List<Validator> validators = new ArrayList<>();
        validators.add(StringValidator.builder().columnDescription("channel_title").build());
        validators.add(IntegerValidator.builder().columnDescription("category_id").build());
        validators.add(DateValidator.builder().columnDescription("publish_time").validationPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").build());
        validators.add(LinkValidator.builder().columnDescription("thumbnail_link").validationPattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").build());
   ```

4. Create a Data validator:
   ```java
        DataValidator dataValidator = DataValidator.builder().columnValidators(columnValidators).firstRowIsHeader(Boolean.TRUE).delimiter(",").build();
        assertEquals(0, dataValidator.validateCsvDataReturningFailures(getFile()).size());
   ```
   